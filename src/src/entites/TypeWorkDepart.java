package entites;

public enum TypeWorkDepart {
    BOOKKEEPING("Бухгалтерия"),
    LOGISTICS("Логистика"),
    REALIZATION("Реализация");

    private String name;

    TypeWorkDepart(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
