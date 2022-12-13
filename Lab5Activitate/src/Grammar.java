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
    Map<String, List<String>> firsts;
    Map<String, List<String>> follows;

    public Grammar(String startSymbol, Set<String> terminals, Set<String> nonTerminals, Map<String, List<List<String>>> productionRules) {
        this.startSymbol = startSymbol;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.productionRules = productionRules;
        initializeFirsts();
        initializeFollows();
    }
    public void initializeFirsts() {
//        for (String nonTerminal : nonTerminals) {
//            List<String> firsts = new ArrayList<>();
//            List<String> rightHandSides = productionRules.get(nonTerminal);
//            if (rightHandSides != null) {
//                rightHandSides.forEach(right -> {
//                    if ( right.substring(0,1) )
//
//                });
//            }
//
//        }
    }

    public void initializeFollows() {

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
                String left = parseLine[0];
                if (startSymbol == null) {
                    startSymbol = left;
                }
                nonTerminals.add(left);


                List<List<String>> productions = productionRules.getOrDefault(left, new ArrayList<>());
                // 1
                String[] rightProductionSide = parseLine[1].split(" ");
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
