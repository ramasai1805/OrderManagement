package gap.constants;

public enum Color  {
    RED(1), GREEN(2), BLUE(3), BROWN(4), GREY(5), WHITE(6), PINK(7), YELLOW(8), BLACK(9), PURPLE(10), ORANGE(11), SKY_BLUE(12);
    int option;

    public int getOption() {
        return option;
    }

    Color(int option) {
        this.option = option;
    }
}
