import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Grammar grammar = Grammar.readFromFile("Lab5Activitate/resources/grammar.in");
        grammar.printLeftRecursiveRules();
    }
}