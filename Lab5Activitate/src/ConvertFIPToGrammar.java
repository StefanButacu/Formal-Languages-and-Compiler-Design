import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertFIPToGrammar {
    public static final List<String> lexicalAtoms = Arrays.asList(
            "ID", "CONSTINT", "CONSTREAL", "CONSTTEXT", "int", "double", "string", "struct",
            "Console.WriteLine", "Console.ReadLine",
            "+", "-", "*", "/", "=", "<", ">", "<=", ">=", "==", "!=", "true", "false", "||", "&&",
            "if", "else", "while", ";", ",", "(", ")", "{", "}");

    public static void main(String[] args) {
        List<String> listInputStrings = new ArrayList<>();
        String FIP_NAME = "Lab5Activitate\\resources\\FIP.txt";
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(FIP_NAME))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(" ");
                Integer atomCode = Integer.valueOf(tokens[0]);
                Integer positionInTable = Integer.valueOf(tokens[1]);
                String lexicalAtom = lexicalAtoms.get(atomCode);
                listInputStrings.add(lexicalAtom);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String[] inputArray = new String[listInputStrings.size()];
        //Converting List to Array
        listInputStrings.toArray(inputArray);

        Grammar grammar = Grammar.readFromFile("Lab5Activitate\\resources\\grammarMLP_Edi_new.txt");
        grammar.generateParsingTable();
        System.out.println(grammar.tree);
        System.out.println(grammar.table);
        System.out.println(grammar.isValid(inputArray));
    }
}
