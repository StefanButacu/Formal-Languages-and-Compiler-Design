import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class KeywordsUtil {
//    public static Set<String> delimiter = new HashSet<>(Arrays.asList(";", "{", "}", "(", ")"));
    public static Set<String> accessModifier = new HashSet<>(Arrays.asList("public", "private", "protected"));
//    public static Set<String> relationalOperator = new HashSet<>(Arrays.asList("<", "<=", ">", ">=", "==", "!="));
//    public static Set<String> arithmeticOperator = new HashSet<>(Arrays.asList("+", "-", "*", "/"));
//    public static Set<String> assignmentOperator = new HashSet<>(Arrays.asList("="));
    public static Set<String> inputOutputInstruction = new HashSet<>(Arrays.asList(
            "System.out.println",
            "System.out.print",
            "System.in",
            "sc.nextInt",
            "sc.nextDouble",
            "sc.nextLine"
    ));
    public static Set<String> entryPointMethod = new HashSet<>(Arrays.asList("main"));
    public static Set<String> keyword = new HashSet<>(Arrays.asList(
            "if", "else", "while", "class",
            "new", "static", "void"
    ));
    public static Set<String> type = new HashSet<>(Arrays.asList(
            "int", "double", "boolean"
    ));
    public static boolean isAccessingNonStaticField(String value){
        return Pattern.compile("[a-z]+\\.[a-z]").matcher(value).matches();
    }

    public static boolean isAccessingStaticField(String value){
        return Pattern.compile("[A-Z][a-z]+\\.[a-z]").matcher(value).matches();
    }

    public static boolean isID(String id){
        return Pattern.compile("^[a-z]{1,8}$").matcher(id).matches();
    }

    public static boolean isClassName(String id){
        return Pattern.compile("^[A-Z][a-z]+$").matcher(id).matches();

    }
    public static boolean isConst(String value){
        return Pattern.compile("^[+-]?([1-9]?[0-9]*)(\\.[0-9]+)?").matcher(value).matches();
    }

    public static boolean isArray(String value){
        return Pattern.compile("^[A-Z][a-z]+\\[]$").matcher(value).matches() ||
                value.equals("int[]");
    }

    public static boolean isSingleLogicalOperator(String c) {
        return Pattern.compile("^([<>])$").matcher(c).matches();
    }

    public static boolean isArithmeticOperator(String c) {
        return Pattern.compile("^([-+*/%])$").matcher(c).matches();
    }

    public static boolean isInstructionDelimiter(String c) {
        return Pattern.compile("^(;)$").matcher(c).matches();

    }

    public static boolean isBlockOpenDelimiter(String c) {
        return Pattern.compile("^(\\{)$").matcher(c).matches();
    }
    public static boolean isBlockClosedDelimiter(String c) {
        return Pattern.compile("^(})$").matcher(c).matches();

    }

    public static boolean isConditionOpenDelimiter(String c) {
        return Pattern.compile("^(\\()$").matcher(c).matches();

    }
    public static boolean isConditionCloseDelimiter(String c) {
        return Pattern.compile("^(\\))$").matcher(c).matches();

    }

    public static boolean isOtherDelimiter(String c) {
        return Pattern.compile("^(!)$").matcher(c).matches();
    }

    public static boolean isSpace(String c) {
        return c.equals(" ");
    }


    public static boolean isKeyword(String token) {
        return accessModifier.contains(token) || inputOutputInstruction.contains(token) ||
                entryPointMethod.contains(token) || keyword.contains(token) ||
                type.contains(token);
    }

    public static boolean isCompoundLogicalOperator(String token) {
        return Pattern.compile("^(==|!=|<=|>=)").matcher(token).matches();
    }

    public static boolean isAttributionOperator(String c) {
        return Pattern.compile("^(=)$").matcher(c).matches();
    }

    public static boolean isDoubleQuotes(String c) {
        return c.equals("\"");
    }
}
