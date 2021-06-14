package entites;

public enum TypePosition {
    WORKER("Рабочий", 0),
    CHIEF_DEPART("Начальник отдела", 1),
    PRE_DIRECTOR("Заместитель директора", 2),
    DIRECTOR("Директор", 3);

    private String name;
    private int priority;

    TypePosition(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}
