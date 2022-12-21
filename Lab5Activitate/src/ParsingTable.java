import java.util.*;

public class ParsingTable {
    Map<Integer, Map<String, Action>> transitions = new HashMap<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Integer from : transitions.keySet()) {
            for (String with : transitions.get(from).keySet()) {
                sb.append(from).append(" -").append(with).append("- ")
                        .append(transitions.get(from).get(with))
                        .append("\n");
            }
        }
        return sb.toString();
    }
}
