import FinitStateMachine.FiniteStateMachine;
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
    private FiniteStateMachine idStateMachine;
    private FiniteStateMachine integersStateMachine;
    private FiniteStateMachine realNumbersStateMachine;
    public Lexer(Map<String, Integer> atomsTable) {
        this.atomsTable = atomsTable;
        internalProgramForm = new ArrayList<>();
        idSymbolsTable = new MyHashTable();
        constSymbolsTable = new MyHashTable();
        parseSucceeded = true;
        idStateMachine = FiniteStateMachine.readFromFile("LAB1\\resources\\finiteStateMachines\\Java-IDS.txt");
        integersStateMachine = FiniteStateMachine.readFromFile("LAB1\\resources\\finiteStateMachines\\Cpp-integers.txt");
        realNumbersStateMachine = FiniteStateMachine.readFromFile("LAB1\\resources\\finiteStateMachines\\real-numbers.txt");
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
                     if (KeywordsUtil.isArray(token)) {
                        System.out.println(token + " -> Array");
                        Integer atomCode = findAtomCode(token);
                        internalProgramForm.add(new Pair<>(atomCode, 0));
                        foundClassIdConstArray = true;
                    }
                     else if (KeywordsUtil.isClassName(token)) {
                         System.out.println(token + " -> ClassName");   // ClassName  syombolsTable
                         Integer atomCode = findAtomCode(token);
                         internalProgramForm.add(new Pair<>(atomCode, 0));
                         foundClassIdConstArray = true;
                     }
                    else if (KeywordsUtil.isID(token)) {
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

    public void parseLines(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber =  1;
            while((line = reader.readLine()) != null ){
                parseLine(line.strip(), lineNumber);
                lineNumber++;
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void parseLine(String line, int lineNumber) {
        int left = 0, right = 0, len = line.length() - 1;
        while(right <= len && left <= right){
            Delimiter delimiterType = getDelimiterType(line.substring(right, right+1));
            if(delimiterType == null){
                right++;
            }
            if(delimiterType != null && left == right) {
                String delimiter;
                if(right + 2 <= len) {
                    delimiter = line.substring(right, right + 2);
                    if(KeywordsUtil.isCompoundLogicalOperator(delimiter)) {
                        System.out.println(delimiter + " -> " + "Logical operator");
                        Integer atomCode = findAtomCode(delimiter);
                        internalProgramForm.add(new Pair<>(atomCode, 0));
                        right += 2;
                    } else {
                        delimiter = line.substring(right, right+1);
                        if(!KeywordsUtil.isSpace(delimiter)){
                            System.out.println(delimiter + "->" + delimiterType.name());
                        }
                        right++;
                    }
                }
                else {
                    delimiter = line.substring(right, right + 1);
                    if(!KeywordsUtil.isSpace(delimiter)){
                        System.out.println(delimiter + " -> " + delimiterType.name());
                    }
                    right++;
                }
                left = right;
            } else if ( delimiterType != null || right == len) {
                String token = line.substring(left, right);
                if(KeywordsUtil.isKeyword(token)){
                    /// print keyword type
                    System.out.println(token + "->" + " Keyword");
                    Integer atomCode = findAtomCode(token);
                    internalProgramForm.add(new Pair<>(atomCode, 0));
                }else{
                    // is constant or id
//                    if (KeywordsUtil.isID(token)) {
                     if (idStateMachine.acceptsSequence(token)) {
                        Integer atomCode = findAtomCode("ID");
                        Integer positionInIdTable = findPositionInIDTable(token);
                        internalProgramForm.add(new Pair(atomCode, positionInIdTable));
                        System.out.println(token + " -> ID");         //  id symbolsTable
                    }
                    else if (KeywordsUtil.isClassName(token)) {
                        System.out.println(token + " -> ClassName");   // ClassName  syombolsTable
                        Integer atomCode = findAtomCode(token);
                        internalProgramForm.add(new Pair<>(atomCode, 0));
                    }
                    else if (KeywordsUtil.isArray(token)) {
                        System.out.println(token + " -> Array");
                        Integer atomCode = findAtomCode(token);
                        internalProgramForm.add(new Pair<>(atomCode, 0));
                    }
//                    else if (KeywordsUtil.isConst(token)) {
                    else if (integersStateMachine.acceptsSequence(token)){
                        Integer atomCode = findAtomCode("CONST");
                        Integer positionInConstTable = findPositionInConstTable(token);
                        internalProgramForm.add(new Pair(atomCode, positionInConstTable));
                        System.out.println(token + " -> Integer");     // const symbolTable
                     } else if (realNumbersStateMachine.acceptsSequence(token)){
                        Integer atomCode = findAtomCode("CONST");
                        Integer positionInConstTable = findPositionInConstTable(token);
                        internalProgramForm.add(new Pair(atomCode, positionInConstTable));
                        System.out.println(token + " -> RealNumber");     // const symbolTable
                    } else {
                        parseSucceeded = false;
                        System.err.println("Error at line " + lineNumber + ". Invalid symbol " + token);
                        return;
                    }
                }
                left = right;
            }
        }
        parseSucceeded = true;
    }

    private Delimiter getDelimiterType(String c) {
        if (KeywordsUtil.isSingleLogicalOperator(c)) {
            return Delimiter.LOGICAL_OPERATOR;
        }
        else if(KeywordsUtil.isArithmeticOperator(c)){
            return Delimiter.ARITHMETIC_OPERATOR;
        } else if (KeywordsUtil.isAttributionOperator(c)) {
            return Delimiter.ATTRIBUTION_OPERATOR;
        } else if (KeywordsUtil.isInstructionDelimiter(c) ||
                    KeywordsUtil.isBlockClosedDelimiter(c) ||
                    KeywordsUtil.isBlockOpenDelimiter(c)||
                    KeywordsUtil.isConditionOpenDelimiter(c)||
                    KeywordsUtil.isConditionCloseDelimiter(c)||
                    KeywordsUtil.isOtherDelimiter(c) ||
                    KeywordsUtil.isSpace(c) ||
                    KeywordsUtil.isDoubleQuotes(c))
        {
            return Delimiter.DELIMITER;
        }

        return null;
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
