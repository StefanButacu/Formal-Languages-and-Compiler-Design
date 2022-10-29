package FinitStateMachine;

public class Main {
    public static void main(String[] args) {

        String filename = "LAB1\\resources\\finiteStateMachineExample.txt";
        FiniteStateMachine machine = FiniteStateMachine.readFromFile(filename);
//        String goodSequence = "cd";
//        String badSequence = "ca";
//        System.out.println(machine.acceptsSequence(goodSequence));
//        System.out.println(machine.acceptsSequence(badSequence));
//        System.out.println(machine.acceptsSequence("d"));
//        System.out.println(machine.acceptsSequence("abcd"));
//        System.out.println(machine.acceptsSequence("aaa"));
//        System.out.println(machine.acceptsSequence("cc"));
        System.out.println(machine.findLongestAcceptedPrefix("ddddddddddddda"));

    }
}
