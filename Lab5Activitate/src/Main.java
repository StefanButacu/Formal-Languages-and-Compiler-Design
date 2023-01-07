public class Main {
    public static void main(String[] args) {
        Grammar grammar = Grammar.readFromFile("Lab5Activitate/resources/grammar.in");
        try {
            grammar.generateParsingTable();
            System.out.println(grammar.tree);
            System.out.println(grammar.table);
            String[] inputSeq = new String[]{"a", "a"};
            boolean valid = grammar.isValid(inputSeq);
            System.out.println(valid);
            if (valid) {
                grammar.printUsedRules();
            }
        } catch (RuntimeException ex) {
            System.out.println(ex.getMessage());
        }
    }
}