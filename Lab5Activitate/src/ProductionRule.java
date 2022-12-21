import java.util.List;

public class ProductionRule {
    public Integer number;
    public String from;
    public List<String> to;

    public ProductionRule(Integer number, String from, List<String> to) {
        this.number = number;
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rule ").append(number).append(": ").append(from)
                .append(" -> ");
        for (String term : to)
            sb.append(term).append(" ");
        return sb.toString();
    }
}
