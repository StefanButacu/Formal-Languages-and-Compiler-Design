public class Main {
    public static void main(String[] args) {
        Grammar grammar = Grammar.readFromFile("Lab5Activitate/resources/otherGrammar.in");
        grammar.generateParsingTable();
        System.out.println(grammar.tree);
        grammar.printFirst();
    }
}