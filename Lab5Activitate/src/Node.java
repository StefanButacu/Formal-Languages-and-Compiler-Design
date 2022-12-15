import java.util.ArrayList;
import java.util.List;

public class Node {
    public List<DotProductionRule> items = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DotProductionRule rule : items)
            sb.append(rule).append("\n");
        return sb.toString();
    }
}
