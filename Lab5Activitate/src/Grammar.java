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
    Map<String, Set<String>> follows = new HashMap<>();

    public Grammar(String startSymbol, Set<String> terminals, Set<String> nonTerminals, Map<String, List<List<String>>> productionRules) {
        this.startSymbol = startSymbol;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.productionRules = productionRules;
        initializeFirsts();
        initializeFollows();
    }

    public Set<String> getFirst(List<String> terms) {
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

    public void initializeFirsts() {
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
        boolean hasChanged = false;

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

    private boolean isTerminal(String s) {
        return terminals.contains(s);
    }

    public void initializeFollows() {
        for (String nonTerminal : nonTerminals) {
            if (nonTerminal.equals("S"))
                follows.put(nonTerminal, Set.of("$"));
            else
                follows.put(nonTerminal, new HashSet<>());
        }

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
