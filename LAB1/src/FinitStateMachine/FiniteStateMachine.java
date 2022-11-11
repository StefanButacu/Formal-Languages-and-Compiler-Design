package FinitStateMachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FiniteStateMachine {

    public String[] states;
    public Character[] alphabet;
    public String initialState;
    public String[] finalStates;
    public List<String[]> transitions;

    private Map<String, List<Edge>> graph;

    public boolean isDeterministic;

    public FiniteStateMachine(String[] states, Character[] alphabet, String initialState, String[] finalStates, List<String[]> transitions) {
        this.states = states;
        this.alphabet = alphabet;
        this.initialState = initialState;
        this.finalStates = finalStates;
        this.transitions = transitions;
        this.isDeterministic = true;
        buildGraph();
    }

    private void buildGraph() {
        graph = new HashMap<>();

        for(String[] transition: transitions){
            String source = transition[0];
            Character symbol = transition[1].toCharArray()[0];
            String destination = transition[2];
            Edge edge = new Edge(destination, symbol);
            List<Edge> oldDestinations = graph.get(source);
            if(oldDestinations == null){
                oldDestinations = new ArrayList<>();
                oldDestinations.add(edge);
                graph.put(source, oldDestinations);
            }
            else{
                oldDestinations.add(edge);
            }
            if(isDeterministic) {
                checkDuplicateEdge(oldDestinations);
            }
        }
    }

    private void checkDuplicateEdge(List<Edge> oldDestinations) {
        for(int i = 0 ; i < oldDestinations.size(); i++){
            for(int j = i + 1; j < oldDestinations.size(); j++){
                Edge edge1 = oldDestinations.get(i);
                Edge edge2 = oldDestinations.get(j);
                if(edge1.symbol.equals(edge2.symbol))
                    isDeterministic = false;
            }
        }
    }

    public boolean acceptsSequence(String symbolsSequence){
        String node = this.initialState;
        for(Character symbol: symbolsSequence.toCharArray()){
            List<Edge> destinations = graph.get(node);
            if(destinations == null){
                return false;
            }
            boolean canContinue = false;
            for(Edge edge: destinations){
                if(edge.symbol.equals(symbol)){
                    node = edge.destinationState;
                    canContinue = true;
                    break;
                }
            }
            if(!canContinue)
                return false;
        }
        return isFinalState(node);
    }

    public String findLongestAcceptedPrefix(String symbolsSequence){
        String node = this.initialState;
        String longestPrefix = "";
        StringBuilder currentSequence = new StringBuilder();
        if(isFinalState(this.initialState)){
            currentSequence.append("E");
        }
        for(Character symbol: symbolsSequence.toCharArray()){
            if(isFinalState(node)){
                longestPrefix = currentSequence.toString();
            }
            List<Edge> destinations = graph.get(node);
            if(destinations == null){
                return longestPrefix;
            }
            boolean canContinue = false;
            for(Edge edge: destinations){
                if(edge.symbol.equals(symbol)){
                    node = edge.destinationState;
                    currentSequence.append(symbol);
                    canContinue = true;
                    break;
                }
            }
            if(!canContinue)
                return longestPrefix;
        }
        if(isFinalState(node)){
            longestPrefix = currentSequence.toString();
        }
        return longestPrefix;
    }
    private boolean isFinalState(String node) {
        return Arrays.asList(finalStates).contains(node);
    }

    public static FiniteStateMachine readFromFile(String filename){
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String[] states = reader.readLine().strip().split(" ");
            String[] alphabet =reader.readLine().strip().split(" ");
            Character[] alphabetChars = new Character[alphabet.length];
            for(int i = 0 ; i < alphabet.length; i++){
                alphabetChars[i] = alphabet[i].toCharArray()[0];
            }
            String initialState = reader.readLine().strip();
            String[] finalStates = reader.readLine().strip().split(" ");
            List<String[]> transitions = new ArrayList<>();
            String line;
            while( (line = reader.readLine()) != null) {
                line = line.strip();
                if(line.isEmpty())
                    continue;
                String[] transition = line.split(" ");
                transitions.add(transition);
            }
            return new FiniteStateMachine(states, alphabetChars, initialState, finalStates, transitions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Edge{
        public String destinationState;
        public Character symbol;

        public Edge(String destinationState, Character symbol) {
            this.destinationState = destinationState;
            this.symbol = symbol;
        }
    }
}
