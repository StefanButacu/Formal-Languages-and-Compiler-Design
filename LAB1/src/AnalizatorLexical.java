import MyDS.MyHashTable;
import MyDS.MyNode;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalizatorLexical {

    public static Map<String, Integer> symbolsTable = new HashMap<>();
    public static void main(String[] args) {

        loadSymbolTable("LAB1\\resources\\SymbolsTable.txt");
        Lexer lexer = new Lexer(symbolsTable);
        String fileName = "LAB1\\resources\\p1.txt";
        System.out.println("P1: ");
        lexer.parse(fileName);
        if(lexer.isParseSucceeded()) {
            String fipFile = "LAB1\\resources\\FIP.csv";
            String idTable = "LAB1\\resources\\idTable.csv";
            String constTable = "LAB1\\resources\\constTable.csv";
            List<Pair> internalProgramForm = lexer.getInternalProgramForm();
            writeInternalFromToFile(internalProgramForm, fipFile);
            MyHashTable idSymbolsTable = lexer.getIdSymbolsTable();
            MyHashTable constSymbolsTable = lexer.getConstSymbolsTable();
            writeSyombolsTable(idSymbolsTable, idTable);
            writeSyombolsTable(constSymbolsTable, constTable);
        }

    }

    private static void writeSyombolsTable(MyHashTable symbolsTable, String filename) {
        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.write("PositionInTable,Atom\n");
            for(MyNode node: symbolsTable.getAll()){
                StringBuilder sb = new StringBuilder();
                sb.append(node.positionInTable);
                sb.append(',');
                sb.append(node.lexicalAtom);
                sb.append('\n');
                writer.write(sb.toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }


    private static void writeInternalFromToFile(List<Pair> internalProgramForm, String filename){

        try (PrintWriter writer = new PrintWriter(filename)) {
            writer.write("AtomCode,PositionInSymbolTable\n");
            for(Pair p: internalProgramForm){
                StringBuilder sb = new StringBuilder();
                sb.append(p.first);
                sb.append(',');
                sb.append(p.second);
                sb.append('\n');
                writer.write(sb.toString());
            }

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
    private static void loadSymbolTable(String filename) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(" ");
                Integer code = Integer.valueOf(tokens[0]);
                String symbol = tokens[1];
                symbolsTable.put(symbol, code);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
