package entites;

public enum TypeGender {
    MALE("Мужчина"),
    FEMALE("Женщина");

    private String name;

    TypeGender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
