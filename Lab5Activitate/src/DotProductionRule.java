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

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DotProductionRule))
            return false;
        DotProductionRule dot = (DotProductionRule) o;

        if (!this.from.equals(dot.from) || this.to.size() != dot.to.size() ||
                this.lookAhead.size() != dot.lookAhead.size())
            return false;
        for (int i = 0; i < this.to.size(); i++)
            if (!this.to.get(i).equals(dot.to.get(i)))
                return false;
        if (this.dotPosition != dot.dotPosition)
            return false;
        return this.lookAhead.equals(dot.lookAhead);
    }
}
