import java.util.List;
import java.util.Set;

public class DotProductionRule {
    String from;
    List<String> to;
    int dotPosition;
    Set<String> lookAhead;

    public DotProductionRule(String from, List<String> to, int dotPosition, Set<String> lookAhead) {
        this.from = from;
        this.to = to;
        this.dotPosition = dotPosition;
        this.lookAhead = lookAhead;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(from).append(" ->");
        for (int i = 0; i < to.size(); i++) {
            String term = to.get(i);
            sb.append(" ");
            if (i == dotPosition)
                sb.append(".");
            sb.append(term);
        }
        sb.append(", ");
        for (String term : lookAhead)
            sb.append(term).append(" ");
        return sb.toString();
    }
}
