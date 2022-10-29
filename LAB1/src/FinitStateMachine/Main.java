package FinitStateMachine;

public class Main {
    public static void main(String[] args) {

        String filename = "LAB1\\resources\\finiteStateMachineExample.txt";
        FiniteStateMachine machine = FiniteStateMachine.readFromFile(filename);
        String[] goodSequence = new String[]{"c", "d"};
        String[] badSequence = new String[]{"c", "a"};
        machine.acceptsSequence(goodSequence);
        machine.acceptsSequence(badSequence);
        System.out.println(machine.states);
        System.out.println(machine.alphabet);
        System.out.println(machine.transitions);
        System.out.println(machine.initialState);
    }
}
