package MyDS;

import java.util.ArrayList;
import java.util.List;

public class MyHashTable {
//    private List<MyNode> bucketArray;
    MyNode[] bucketArray;
    private int numBuckets;

    private int size;

    public MyHashTable() {
        numBuckets = 2;
        size = 0;
        bucketArray = new MyNode[numBuckets];
        for(int i = 0; i < numBuckets ; i++){
            bucketArray[i] = null;
        }
    }

    private int getBucketIndex(String lexicalAtom) {
        int hashCode = hashCode(lexicalAtom);
        int index = hashCode % numBuckets;
        // lexicalAtom.hashCode() could be negative.
        index = index < 0 ? index * -1 : index;
        return index;
    }

    private int hashCode(String key) {
        return key.hashCode() % numBuckets;
    }

    public Integer findOrAddAtomWithId(String lexicalAtom) {
        Integer position = findPositionForAtomWithId(lexicalAtom);
        if( position == null){
            position = addLexicalAtom(lexicalAtom);
        }
        return position;

    }

    private Integer addLexicalAtom(String lexicalAtom) {
        Integer position;
        MyNode toInsertNode = new MyNode(lexicalAtom, String.class);
        size++;
        toInsertNode.positionInTable = size;
        // insert node in the list pointed by head
        int bucketIndex = getBucketIndex(lexicalAtom);
        int hashCode = hashCode(lexicalAtom);
        MyNode head = bucketArray[bucketIndex];
        toInsertNode.next = head;
        bucketArray[bucketIndex] = toInsertNode;

        ///  - double the capacity of the array if load factor > 0.7
        if ((1.0 * size) / numBuckets >= 0.7) {
            MyNode[] temp = bucketArray;
            bucketArray = new MyNode[2*numBuckets];
            numBuckets = 2 * numBuckets;
            size = 0;
            for (int i = 0; i < numBuckets; i++)
                bucketArray[i]= null;

            for (MyNode headNode : temp) {
                while (headNode != null) {
                    addLexicalAtom(headNode.lexicalAtom);
                    headNode = headNode.next;
                }
            }
        }

        return toInsertNode.positionInTable;

    }

    private Integer findPositionForAtomWithId(String lexicalAtom) {
        int bucketIndex = getBucketIndex(lexicalAtom);
        MyNode head = bucketArray[bucketIndex];
        // Insert lexicalAtom in chain
        while(head != null){
            if(head.lexicalAtom.equals(lexicalAtom)) {
                return head.positionInTable;
            }
            head = head.next;
        }
        return null;
    }
    public List<MyNode> getAll(){
        List<MyNode> nodes = new ArrayList<>();
        for(int i = 0 ; i < bucketArray.length; i++) {
            MyNode head = bucketArray[i];
            if(head != null){
                while(head != null){
                    nodes.add(head);
                    head = head.next;
                }
            }
        }
        return nodes;
    }
}
