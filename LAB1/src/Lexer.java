import MyDS.MyHashTable;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class Lexer {

    private Map<String, Integer> atomsTable;
    private ArrayList<Pair> internalProgramForm;

    private MyHashTable idSymbolsTable;
    private MyHashTable constSymbolsTable;

    private boolean parseSucceeded;
    public Lexer(Map<String, Integer> atomsTable) {
        this.atomsTable = atomsTable;
        internalProgramForm = new ArrayList<>();
        idSymbolsTable = new MyHashTable();
        constSymbolsTable = new MyHashTable();
        parseSucceeded = true;
    }

    public void parse(String fileName){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int lineNumber = 0;
            while ( (line = bufferedReader.readLine()) != null){
            String[] tokens = line.split(" ");
            lineNumber++;
            Field[] fields = KeywordsUtil.class.getDeclaredFields();
            for(String token : tokens) {
                token = token.strip();
                if(token.isEmpty())
                    continue;
                boolean foundKeywordOperatorSeparator = false;
                for (Field field : fields) {
                    Set<String> tokenSet = (Set<String>) field.get(KeywordsUtil.class);
                    for(String tokenString : tokenSet){
                        if(tokenString.equals(token) && !foundKeywordOperatorSeparator){
                            System.out.println(token + " -> " + field.getName());
                            foundKeywordOperatorSeparator = true;
                            Integer atomCode = findAtomCode(token);
                            internalProgramForm.add(new Pair<>(atomCode, 0));
                            break;
                        }
                    }
                }
                boolean foundClassIdConstArray = false;
                if(!foundKeywordOperatorSeparator) {
                    // call methods
                    if (KeywordsUtil.isID(token)) {
                        Integer atomCode = findAtomCode("ID");
                        Integer positionInIdTable = findPositionInIDTable(token);
                        internalProgramForm.add(new Pair(atomCode, positionInIdTable));
                        System.out.println(token + " -> ID");         //  id symbolsTable
                        foundClassIdConstArray = true;
                    }
                    else if (KeywordsUtil.isConst(token)) {
                        Integer atomCode = findAtomCode("CONST");
                        Integer positionInConstTable = findPositionInConstTable(token);
                        internalProgramForm.add(new Pair(atomCode, positionInConstTable));
                        System.out.println(token + " -> CONST");     // const symbolTable
                        foundClassIdConstArray = true;
                    }
                    else if (KeywordsUtil.isClassName(token)) {
                        System.out.println(token + " -> ClassName");   // ClassName  syombolsTable
                        Integer atomCode = findAtomCode(token);
                        internalProgramForm.add(new Pair<>(atomCode, 0));
                        foundClassIdConstArray = true;
                    } else if (KeywordsUtil.isArray(token)) {
                        System.out.println(token + " -> Array");
                        Integer atomCode = findAtomCode(token);
                        internalProgramForm.add(new Pair<>(atomCode, 0));
                        foundClassIdConstArray = true;
                    }
                }
                if(!foundKeywordOperatorSeparator && !foundClassIdConstArray ) {
                    parseSucceeded = false;
                    System.err.println("Error at line " + lineNumber + ". Invalid symbol " + token);
                    return;
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

        parseSucceeded = true;
    }

    private Integer findPositionInConstTable(String token) {
        return constSymbolsTable.findOrAddAtomWithId(token);
    }

    /**
     * Get the positing of the ID from IdTables if it exists
     * Otherwise insert ID and return new position
     * @param ID - to be search id
     * @return - position
     */
    private Integer findPositionInIDTable(String ID) {
        // lexicalAtom - ID -> id = 'a'
        return idSymbolsTable.findOrAddAtomWithId(ID);

    }

    private Integer findAtomCode(String token) {
        return atomsTable.get(token);
    }

    public Map<String, Integer> getAtomsTable() {
        return atomsTable;
    }

    public ArrayList<Pair> getInternalProgramForm() {
        return internalProgramForm;
    }

    public void setAtomsTable(Map<String, Integer> atomsTable) {
        this.atomsTable = atomsTable;
    }

    public void setInternalProgramForm(ArrayList<Pair> internalProgramForm) {
        this.internalProgramForm = internalProgramForm;
    }

    public boolean isParseSucceeded() {
        return parseSucceeded;
    }

    public void setParseSucceeded(boolean parseSucceeded) {
        this.parseSucceeded = parseSucceeded;
    }

    public MyHashTable getIdSymbolsTable() {
        return idSymbolsTable;
    }

    public void setIdSymbolsTable(MyHashTable idSymbolsTable) {
        this.idSymbolsTable = idSymbolsTable;
    }

    public MyHashTable getConstSymbolsTable() {
        return constSymbolsTable;
    }

    public void setConstSymbolsTable(MyHashTable constSymbolsTable) {
        this.constSymbolsTable = constSymbolsTable;
    }
}
