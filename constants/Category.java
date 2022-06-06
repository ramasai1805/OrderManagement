package gap.constants;

public enum Category {
    SHIRTS(1), PANTS(2), JEANS(3), SHORTS(4), T_SHIRTS(5), POLOS(6), HOODIES(7), KURTA(8), SWEATSHIRTS(9), DRESS(10), TOPS(11);
    int option;

    public int getOption() {
        return option;
    }

    Category(int option) {
        this.option = option;
    }
}
