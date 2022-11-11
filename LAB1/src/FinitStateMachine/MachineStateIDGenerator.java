package FinitStateMachine;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MachineStateIDGenerator {
    public static void main(String[] args) {
        generateMachineStateForID();
    }

    private static void generateMachineStateForID() {
        String filename = "LAB1/resources/Java-IDS.txt";
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            StringBuilder builder = new StringBuilder();
            String q = "q";
            String initialState = q+"0";
            builder.append(initialState);
            builder.append(' ');
            List<String> finalStates = new ArrayList<>();
            for (int i = 1 ; i <= 8; i++) {
                finalStates.add(q + i);
                builder.append(q + i);
                if(i != 8)
                    builder.append(" ");
            }
            builder.append("\n");
            List<String> alphabet = new ArrayList<>();
            for (char c = 'a'; c <= 'z'; c++) {
                alphabet.add(String.valueOf(c));
                builder.append(c);
                if(c != 'z')
                    builder.append(" ");
            }
            builder.append("\n");
            builder.append(initialState);
            builder.append("\n");
            for(String state: finalStates) {
                builder.append(state);
                if(!state.equals("q8")) {
                    builder.append(' ');
                }
            }
            builder.append("\n");
            /// transitions
            /// q0 - q1
            for(int i = 0 ; i < alphabet.size(); i++){
                builder.append(initialState).append(" ").append(alphabet.get(i)).append(" ").append(finalStates.get(0));
                builder.append("\n");
            }
            // q1 - q2 - q3 - .. - q8
            for(int i = 0 ; i < finalStates.size() - 1; i++){
                for (String s : alphabet) {
                    builder.append(finalStates.get(i)).append(" ").append(s).append(" ").append(finalStates.get(i + 1));
                    builder.append("\n");
                }
            }
            bufferedWriter.write(builder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
