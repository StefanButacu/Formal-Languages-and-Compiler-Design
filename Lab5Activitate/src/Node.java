import java.util.ArrayList;
import java.util.List;

public class Node {
    public Integer number;
    public List<DotProductionRule> items = new ArrayList<>();

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (DotProductionRule rule : items)
            sb.append(rule).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Node node))
            return false;

        if (this.items.size() != node.items.size())
            return false;
        for (DotProductionRule rule : this.items)
            if (!node.items.contains(rule))
                return false;
        return true;
    }
}
