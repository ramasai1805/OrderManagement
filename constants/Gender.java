package gap.constants;

public enum Gender {
    MEN(1, "Men"), WOMEN(2, "Women");
    int option;
    String gender;

    public int getOption() {
        return option;
    }

    public String getGender() {
        return gender;
    }

    Gender(int option, String gender) {
        this.option = option;
        this.gender = gender;
    }
}
