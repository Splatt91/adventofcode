package day.thirteen;

public enum State {
    IN_ORDER(-1),
    NOT_IN_ORDER(1),
    SKIP(0);

    private int value;

    State(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static State convertToState(int state) {
        if (state < 0) {
            return IN_ORDER;
        } else if (state > 0) {
            return NOT_IN_ORDER;
        } else {
            return SKIP;
        }
    }
}
