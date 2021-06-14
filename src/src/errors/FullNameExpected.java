package errors;

public class FullNameExpected extends Throwable{

    public FullNameExpected() {
        super("Введено не полное имя");
    }
}
