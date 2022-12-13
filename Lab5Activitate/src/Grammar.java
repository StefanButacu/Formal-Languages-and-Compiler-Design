import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Grammar {
    String startSymbol;
    Set<String> terminals;
    Set<String> nonTerminals;
    Map<String, List<String>> productionRules;

    public Grammar(String startSymbol, Set<String> terminals, Set<String> nonTerminals, Map<String, List<String>> productionRules) {
        this.startSymbol = startSymbol;
        this.terminals = terminals;
        this.nonTerminals = nonTerminals;
        this.productionRules = productionRules;
    }

    public String getLookahead(String nonTerminal) {
        List<String> rightHandSides = productionRules.getOrDefault(nonTerminal, null);
        if(rightHandSides != null ) {
            for (String rightHand : rightHandSides) {
                if ( terminals.contains(rightHand.toCharArray()[0]) ) {
                    return String.valueOf(rightHand.toCharArray()[0]);
                }
            }
        }
        return null;

    }
    public static Grammar readFromFile(String filename) {
        Set<String> terminals = new HashSet<>();
        Set<String> nonTerminals = new HashSet<>();
        Map<String, List<String>> productionRules = new HashMap<>();
        String startSymbol = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.strip();
                if (line.isEmpty())
                    continue;
                String[] parseLine = line.split("-");
                String left = parseLine[0];
                if(startSymbol == null) {
                    startSymbol = left;
                }
                nonTerminals.add(String.valueOf(left));
                List<String> production = productionRules.getOrDefault(left, new ArrayList<>());
                production.add(parseLine[1]);
                productionRules.put(left, production);
                String right = parseLine[1];
                for (Character c : right.toCharArray()) {
                    terminals.add(String.valueOf(c));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // remove from terminals the symbols which appear in non-terminals
        for(String nonTerminal : nonTerminals){
            terminals.remove(nonTerminal);
        }

        return new Grammar(startSymbol, terminals, nonTerminals, productionRules);

    }
        public void printLeftRecursiveRules() {
            for(Map.Entry<String, List<String>> rule: productionRules.entrySet()){
                for(String result: rule.getValue()){
                    if(result.toCharArray()[0] ==  rule.getKey().toCharArray()[0]){
                        System.out.println(rule.getKey() + "->" + result);
                    }
                }
            }
    }
}
