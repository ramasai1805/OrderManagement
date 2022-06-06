package gap.constants;

public enum Size {
    S(1), M(2), L(3), XL(4), XXL(5);
    int option;

    public int getOption() {
        return option;
    }

    Size(int option) {
        this.option = option;
    }
}
