package errors;

public class UserNameExist extends Throwable{

    public UserNameExist() {
        super("Такое имя пользователя уже сущуствует");
    }
}
