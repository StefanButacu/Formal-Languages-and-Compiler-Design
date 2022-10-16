import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Parser {

    private static Set<String> delimiter = new HashSet<>(Arrays.asList(";", "{", "}", "(", ")"));
    private static Set<String> accessModifier = new HashSet<>(Arrays.asList("public", "private", "protected"));
    private static Set<String> relationalOperator = new HashSet<>(Arrays.asList("<", "<=", ">", ">=", "==", "!="));
    private static Set<String> arithmeticOperator = new HashSet<>(Arrays.asList("+", "-", "*", "/"));
    private static Set<String> assignmentOperator = new HashSet<>(Arrays.asList("="));
    private static Set<String> inputOutputInstruction = new HashSet<>(Arrays.asList(
            "System.out.println",
            "System.out.print",
            "System.in",
            "sc.nextInt",
            "sc.nextDouble",
            "sc.nextLine"
    ));
    private static Set<String> entryPointMethod = new HashSet<>(Arrays.asList("main"));
    private static Set<String> keyword = new HashSet<>(Arrays.asList(
            "if", "else", "while", "class",
            "new", "static", "void"
    ));
    private static Set<String> type = new HashSet<>(Arrays.asList(
            "int", "double", "boolean"

    ));
    public static void main(String[] args) throws IllegalAccessException {
        String fileName = "LAB1\\resources\\p1.txt";
        System.out.println("P1: ");
//        System.out.println(Path.of(fileName).toAbsolutePath());
        parse(fileName);
        System.out.println("=======================================");
        fileName = "LAB1\\resources\\p2.txt";
        System.out.println("P2: ");
        parse(fileName);
        System.out.println("=======================================");
        fileName = "LAB1\\resources\\p3.txt";
        System.out.println("P3: ");
        parse(fileName);
        System.out.println("=======================================");
    }

    private static void parse(String fileName){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ( (line = bufferedReader.readLine()) != null){
            String[] tokens = line.split(" ");
            Field[] fields = Parser.class.getDeclaredFields();
            for(String token : tokens) {
                token = token.strip();
                if(token.isEmpty())
                    continue;
                boolean foundToken = false;
                for (Field field : fields) {
                    Set<String> tokenSet = (Set<String>) field.get(Parser.class);
//                    tokenSet.stream().filter(string -> string.equals(token)).findAny().ifPresentOrElse(
//                            (value) -> {
//                                System.out.println(token + "=" + field.getName());
//                            }, () -> System.out.println("Not found")
//                    );

                    for(String tokenString : tokenSet){
                        if(tokenString.equals(token)){
                            System.out.println(token + " -> " + field.getName());
                            foundToken = true;
                            break;
                        }
                    if(foundToken)
                        break;
                    }
                }
             if(!foundToken) {
                 // call methods
                 if (isClassName(token)) {
                     System.out.println(token + " -> ClassName");
                     foundToken = true;
                 } else if (isID(token)) {
                     System.out.println(token + " -> ID");
                     foundToken = true;
                 } else if (isConst(token)) {
                     System.out.println(token + " -> CONST");
                    foundToken = true;
                 }else if (isArray(token)) {
                     System.out.println(token + " -> Array");
                     foundToken = true;
                 }
                 if(!foundToken) {
                     System.out.println(token + " Not found");
                 }

             }
            }
        }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }

    private static boolean isAccessingNonStaticField(String value){
        return Pattern.compile("[a-z]+\\.[a-z]").matcher(value).matches();
    }

    private static boolean isAccessingStaticField(String value){
        return Pattern.compile("[A-Z][a-z]+\\.[a-z]").matcher(value).matches();
    }

    private static boolean isID(String id){
        return Pattern.compile("^[a-z]+$").matcher(id).matches();
    }

    private static boolean isClassName(String id){
        return Pattern.compile("^[A-Z][a-z]+$").matcher(id).matches();

    }
    private static boolean isConst(String value){
        return Pattern.compile("^[+-]?([1-9]?[0-9]*)(\\.[0-9]+)?").matcher(value).matches() ||
                Pattern.compile("^\"[^\"]*\"$").matcher(value).matches();
    }

    private static boolean isArray(String value){
        // isClassName + []
        return Pattern.compile("^[A-Z][a-z]+\\[]$").matcher(value).matches() ||
                value.equals("int[]");
    }
}
