public class Action {
    public ActionType actionType;
    public Integer number;

    public Action(ActionType actionType, Integer number) {
        this.actionType = actionType;
        this.number = number;
    }

    public Action(ActionType actionType) {
        this.actionType = actionType;
    }

    @Override
    public String toString() {
        if (number == null)
            return actionType.name();
        return actionType.name() + number;
    }
}
