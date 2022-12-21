import java.util.HashMap;
import java.util.Map;

public class ParsingTree {
    public Map<Integer, Node> nodes = new HashMap<>();
    public Map<Integer, Map<String, Node>> links = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer key : nodes.keySet())
            sb.append("Node ").append(key).append("\n")
                    .append(nodes.get(key)).append("\n");

        sb.append("\nLinks\n");
        for (Integer key : links.keySet()) {
            for (String term : links.get(key).keySet()) {
                sb.append("Node ").append(key).append(" -")
                    .append(term).append("- ")
                    .append("Node ")
                    .append(links.get(key).get(term).number)
                    .append("\n");
            }
        }
        return sb.toString();
    }
}
