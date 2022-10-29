package FinitStateMachine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FiniteStateMachine {

    public String[] states;
    public String[] alphabet;
    public String initialState;
    public String[] finalStates;
    public List<String[]> transitions;

    private Map<String, List<Edge>> graph;

    public boolean isDeterministic;

    public FiniteStateMachine(String[] states, String[] alphabet, String initialState, String[] finalStates, List<String[]> transitions) {
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
            String symbol = transition[1];
            String destination = transition[2];
            Edge edge = new Edge(destination, symbol);
            List<Edge> oldDestinations = graph.get(source);
            if(oldDestinations == null){
                /// if in old destination exista 2 edgeuri care au acelasi symbol
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

    public boolean acceptsSequence(String[] symbolsSequence){
        String node = this.initialState;
        for(String symbol: symbolsSequence){
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

    private boolean isFinalState(String node) {
        return Arrays.asList(finalStates).contains(node);
    }

    public static FiniteStateMachine readFromFile(String filename){
        try(BufferedReader reader = new BufferedReader(new FileReader(filename))){
            String[] states = reader.readLine().split(" ");
            String[] alphabet = reader.readLine().split(" ");
            String initialState = reader.readLine();
            String[] finalStates = reader.readLine().split(" ");
            List<String[]> transitions = new ArrayList<>();
            String line;
            while( (line = reader.readLine()) != null) {
                String[] transition = line.split(" ");
                transitions.add(transition);
            }
            return new FiniteStateMachine(states, alphabet, initialState, finalStates, transitions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class Edge{
        public String destinationState;
        public String symbol;

        public Edge(String destinationState, String symbol) {
            this.destinationState = destinationState;
            this.symbol = symbol;
        }
    }
}
