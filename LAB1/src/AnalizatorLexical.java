public class AnalizatorLexical {


    public static void main(String[] args) {
        Parser parser = new Parser();
        String fileName = "LAB1\\resources\\p1.txt";
        System.out.println("P1: ");
//        System.out.println(Path.of(fileName).toAbsolutePath());
        parser.parse(fileName);
        System.out.println("=======================================");
        fileName = "LAB1\\resources\\p2.txt";
        System.out.println("P2: ");
        parser.parse(fileName);
        System.out.println("=======================================");
        fileName = "LAB1\\resources\\p3.txt";
        System.out.println("P3: ");
        parser.parse(fileName);
        System.out.println("=======================================");
    }
}
