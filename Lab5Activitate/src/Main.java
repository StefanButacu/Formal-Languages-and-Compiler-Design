public class Main {
    public static void main(String[] args) {
        Grammar grammar = Grammar.readFromFile("Lab5Activitate/resources/grammar.in");
        grammar.printLeftRecursiveRules();
    }
}