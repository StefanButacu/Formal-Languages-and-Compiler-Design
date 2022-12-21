public class Main {
    public static void main(String[] args) {
        Grammar grammar = Grammar.readFromFile("Lab5Activitate/resources/grammar.in");
//        try {
            grammar.generateParsingTable();
            System.out.println(grammar.tree);
            System.out.println(grammar.table);
//        } catch (RuntimeException ex) {
//            System.out.println(ex.getMessage());
//        }
    }
}