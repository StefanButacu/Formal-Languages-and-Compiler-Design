package MyDS;

import java.util.ArrayList;
import java.util.List;

public class MyHashTable {
    private List<MyNode> bucketArray;
    // Current capacity of array list
    private int numBuckets;

    // Current size of array list
    private int size;

    public MyHashTable() {
        numBuckets = 10;
        size = 0;
        bucketArray = new ArrayList<>();
        for(int i = 0; i < 10 ; i++){
            bucketArray.add(null);
        }
    }

    public void addNode(MyNode node){


    }

    private int getBucketIndex(String lexicalAtom)
    {
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
        MyNode head = bucketArray.get(bucketIndex);
        toInsertNode.next = head;
        bucketArray.set(bucketIndex,toInsertNode);

        /// TODO
        ///  - double the capacity of the array if load factor > 0.7

        return toInsertNode.positionInTable;

    }

    private Integer findPositionForAtomWithId(String lexicalAtom) {

        int bucketIndex = getBucketIndex(lexicalAtom);
        MyNode head = bucketArray.get(bucketIndex);
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
        for(int i = 0 ; i < bucketArray.size(); i++){
            MyNode head = bucketArray.get(i);
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
