import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grammar {
    public final static String epsilon = "#";
    String startSymbol;
    Set<String> terminals;
    Set<String> nonTerminals;
    Map<String, List<List<String>>> productionRules;
    Map<String, Set<String>> first = new HashMap<>();
    ParsingTree tree = new ParsingTree();

    public Grammar(String startSymbol, Set<String> terminals, Set<String> nonTerminals, Map<String, List<List<String>>> productionRules) {
        this.startSymbol = startSymbol;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.productionRules = productionRules;
    }

    public void generateParsingTable() {
        enrichGrammar();
        initializeFirst();
        generateParsingTree();
    }

    private void enrichGrammar() {
        String addedNonTerminal = "S'";
        this.nonTerminals.add(addedNonTerminal);
        productionRules.put(addedNonTerminal, List.of(List.of(startSymbol)));
    }

    private void generateParsingTree() {
        tree.nodes.put(0, new Node());
        first.put("$", Set.of("$"));
        tree.nodes.get(0).items.add(new DotProductionRule("S'",
                List.of(startSymbol), 0, Set.of("$")));
        tree.nodes.get(0).number = 0;
        closure(tree.nodes.get(0));
        Set<Integer> newNodeNumbers = goTo(tree.nodes.get(0));
        while (!newNodeNumbers.isEmpty()) {
            Set<Integer> newNodeNumbersCopy = new HashSet<>(newNodeNumbers);
            newNodeNumbers.clear();
            for (Integer nodeNumber : newNodeNumbersCopy) {
                closure(tree.nodes.get(nodeNumber));
                newNodeNumbers.addAll(goTo(tree.nodes.get(nodeNumber)));
            }
            if (newNodeNumbers.isEmpty())
                break;
        }
    }

    private Set<Integer> goTo(Node fromNode) {
        Set<Integer> newNodeNumbers = new HashSet<>();
        for (DotProductionRule rule : fromNode.items) {
            DotProductionRule newRule = new DotProductionRule(
                    rule.from, rule.to, rule.dotPosition + 1, rule.lookAhead
            );
            boolean found = false;
            if (rule.dotPosition >= rule.to.size())
                continue;
            String dotTerm = rule.to.get(rule.dotPosition);
            if (dotTerm.equals(epsilon))
                continue;
            for (Node toNode : tree.nodes.values()) {
                if (toNode.items.get(0).equals(newRule)) {
                    found = true;
                    if (!tree.links.containsKey(fromNode.number))
                        tree.links.put(fromNode.number, new HashMap<>());
                    tree.links.get(fromNode.number).put(dotTerm, toNode);
                }
            }
            if (found)
                continue;
            Integer currentNodeNumber = tree.nodes.size();
            tree.nodes.put(currentNodeNumber, new Node());
            tree.nodes.get(currentNodeNumber).number = currentNodeNumber;
            tree.nodes.get(currentNodeNumber).items.add(newRule);
            if (!tree.links.containsKey(fromNode.number))
                tree.links.put(fromNode.number, new HashMap<>());
            tree.links.get(fromNode.number).put(dotTerm, tree.nodes.get(currentNodeNumber));
            newNodeNumbers.add(currentNodeNumber);
        }
        return newNodeNumbers;
    }

    private void closure(Node node) {
        boolean hasChanged;
        do {
            hasChanged = false;
            Set<DotProductionRule> itemsCopy = new HashSet<>(node.items);
            for (DotProductionRule item : itemsCopy) {
                int dot = item.dotPosition, size = item.to.size();
                if (dot >= item.to.size())
                    continue;
                String dotElement = item.to.get(dot);
                if (isNonTerminal(dotElement)) {
                    // will get the first terminal(s) from the terms list
                    // terms contains the nonterminals and terminals after the dot nonterminal
                    List<String> terms = new ArrayList<>(item.to.subList(dot + 1, size));
                    Set<String> lookahead = getFirst(terms);
                    // to which we add the lookahead of the current production rule,
                    // if the last nonterminal can be epsilon (#)
                    if (lookahead.isEmpty() || lookahead.contains(epsilon))
                        lookahead.addAll(item.lookAhead);
                    for (DotProductionRule rule : getRulesOfNonTerminal(dotElement, lookahead)) {
                        if (!node.items.contains(rule)) {
                            node.items.add(rule);
                            hasChanged = true;
                        }
                    }
                }
            }
        } while (hasChanged);
    }

    private List<DotProductionRule> getRulesOfNonTerminal(String nonTerminal, Set<String> lookahead) {
        List<DotProductionRule> rules = new ArrayList<>();
        for (List<String> right : productionRules.get(nonTerminal))
            rules.add(new DotProductionRule(nonTerminal, right, 0, lookahead));
        return rules;
    }

    private Set<String> getFirst(List<String> terms) {
        Set<String> firsts = new HashSet<>();
        for (int i = 0; i < terms.size(); i++) {
            String term = terms.get(i);
            Set<String> firstOfTerm = first.get(term);
            firsts.addAll(firstOfTerm);
            if (firstOfTerm.contains(epsilon)) {
                if (i != terms.size() - 1) {
                    firsts.remove(epsilon);
                }
            } else {
                break;
            }
        }
        return firsts;
    }

    private void initializeFirst() {
        for (String nonTerminal : nonTerminals) {
            Set<String> firsts = new HashSet<>();
            List<List<String>> rightHandSides = productionRules.get(nonTerminal);
            if (rightHandSides != null) {
                rightHandSides.forEach(right -> {
                    if (isTerminal(right.get(0))) {
                        firsts.add(right.get(0));
                    }
                });
            }
            first.put(nonTerminal, firsts);
        }
        for (String terminal : terminals) {
            first.put(terminal, Set.of(terminal));
        }
        boolean hasChanged;

        do {
            hasChanged = false;
            for (String nonTerminal : nonTerminals) {
                List<List<String>> rightHandSides = productionRules.get(nonTerminal);
                Set<String> newFirst = first.get(nonTerminal);
                Set<String> oldFirst = new HashSet<>(first.get(nonTerminal));

                for (List<String> rightHand : rightHandSides) {
                    for (int i = 0; i < rightHand.size(); i++) {
                        Set<String> firstOfSymbol = first.get(rightHand.get(i));
                        newFirst.addAll(firstOfSymbol);
                        if (firstOfSymbol.contains(epsilon)) {
                            if (i != rightHand.size() - 1) {
                                newFirst.remove(epsilon);
                            }
                        } else {
                            break;
                        }
                    }
                }
                if (newFirst.size() != oldFirst.size()) {
                    hasChanged = true;
                }
            }
        } while (hasChanged);
    }

    public void printFirst() {
        for (String term : first.keySet()) {
            if (isTerminal(term) || isEndTerm(term))
                continue;
            System.out.print(term + ": ");
            for (String terminal : first.get(term))
                System.out.print(terminal + ", ");
            System.out.println();
        }
    }

    private boolean isNonTerminal(String s) {
        return nonTerminals.contains(s);
    }

    private boolean isTerminal(String s) {
        return terminals.contains(s);
    }

    private boolean isEndTerm(String s) {
        return s.equals("$");
    }

    public static Grammar readFromFile(String filename) {
        Set<String> terminals = new HashSet<>();
        Set<String> nonTerminals = new HashSet<>();
        Map<String, List<List<String>>> productionRules = new HashMap<>();
        String startSymbol = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.strip();
                if (line.isEmpty())
                    continue;
                String[] parseLine = line.split("-");
                String left = parseLine[0].strip();
                if (startSymbol == null) {
                    startSymbol = left;
                }
                nonTerminals.add(left);
                List<List<String>> productions = productionRules.getOrDefault(left, new ArrayList<>());
                String[] rightProductionSide = parseLine[1].strip().split(" ");
                productions.add(List.of(rightProductionSide));
                productionRules.put(left, productions);

                terminals.addAll(List.of(rightProductionSide));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // remove from terminals the symbols which appear in non-terminals
        for (String nonTerminal : nonTerminals) {
            terminals.remove(nonTerminal);
        }

        return new Grammar(startSymbol, terminals, nonTerminals, productionRules);
    }

}
