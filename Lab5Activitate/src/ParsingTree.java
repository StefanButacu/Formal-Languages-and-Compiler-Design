import java.util.HashMap;
import java.util.Map;

public class ParsingTree {
    public Map<Integer, Node> nodes = new HashMap<>();
    public Map<Integer, Map<Character, Node>> goTos = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer key : nodes.keySet())
            sb.append("Node ").append(key).append("\n").append(nodes.get(key));
        return sb.toString();
    }
}
