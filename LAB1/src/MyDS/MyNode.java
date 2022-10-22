package MyDS;

public class MyNode {

    public String lexicalAtom;  // name of the constant/id

    public Integer positionInTable;
    public Class<?> valueClass;

    public MyNode next;

    public MyNode(String lexicalAtom, Class<?> valueClass) {
        this.lexicalAtom = lexicalAtom;
        this.valueClass = valueClass;
    }


}

