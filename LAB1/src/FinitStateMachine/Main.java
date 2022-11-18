package FinitStateMachine;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    static FiniteStateMachine stateMachine;
    static Scanner scanner;
    private static void printMenu() {
        String[] menu = {
                "Menu:",
                "1.States",
                "2.Alphabet",
                "3.Transitions",
                "4.Initial state",
                "5.Final states",
                "6.Verify if a sequence is accepted",
                "7.Longest accepted prefix",
                "0.Exit"
        };
        for (String s : menu) {
            System.out.println(s);
        }
    }

    private static void run() {
        while (true) {
            printMenu();
            String cmd = scanner.nextLine();
            if (Objects.equals(cmd, "1")) {
                printStates();
            } else if (Objects.equals(cmd, "2")) {
                printAlphabet();
            } else if (Objects.equals(cmd, "3")) {
                printTransactions();
            } else if (Objects.equals(cmd, "4")) {
                System.out.println("Initial state: "+ stateMachine.initialState);
            } else if (Objects.equals(cmd, "5")) {
                printFinalStates();
            } else if (Objects.equals(cmd, "6")) {
                verifyASSequenceIsAccepted();
            } else if (Objects.equals(cmd, "7")) {
                printLongestAcceptedPrefix();
            }  else {
                return;
            }
        }
    }

    private static void printLongestAcceptedPrefix() {
        System.out.println("Enter the sequence: ");
        String sequence = scanner.nextLine();
        String longestAcceptedPrefix = stateMachine.findLongestAcceptedPrefix(sequence);
        System.out.println(longestAcceptedPrefix.isEmpty() ? "Empty seq" : "Longest prefix: " + longestAcceptedPrefix);

    }

    private static void verifyASSequenceIsAccepted() {
        System.out.println("Enter the sequence: ");
        String sequence = scanner.nextLine();
        boolean isAccepted = stateMachine.acceptsSequence(sequence);
        if(isAccepted)
            System.out.println("Accepted");
        else System.out.println("Not accepted");
    }

    private static void printFinalStates() {
        System.out.print("Final states: ");
        Arrays.stream(stateMachine.finalStates).forEach(state -> System.out.print(state + " "));
        System.out.println();
    }
    private static void printTransactions() {
        System.out.println("Transitions: ");
        List<String[]> transitions = stateMachine.transitions;
        transitions.forEach(transition ->{
            System.out.println(transition[0] + "->" + transition[1] + "->" + transition[2]);});
        System.out.println();
    }

    private static void printAlphabet() {
        System.out.println("Alphabet: ");
        Character[] alphabet = stateMachine.alphabet;
        Arrays.stream(alphabet).forEach(symbol -> System.out.print(symbol + " "));
        System.out.println();
    }

    private static void printStates() {
        System.out.println("States: ");
        String[] states = stateMachine.states;
        Arrays.stream(states).forEach(state -> System.out.print(state + " "));
        System.out.println();
    }

    public static void main(String[] args) {
        String filename = "LAB1\\resources\\finiteStateMachines\\Java-integers.txt";
//        String filename = "LAB1\\resources\\classExample.txt";

        stateMachine = FiniteStateMachine.readFromFile(filename);
        scanner = new Scanner(System.in);
        run();
    }
}
