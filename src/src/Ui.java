import java.io.Serializable;
import java.util.Scanner;

public class Ui implements Serializable {

    private String userPassword;
    private String userName;

    public Ui() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Создайте логин для нового сотрудника");
        this.userName = scanner.nextLine().trim();
        System.out.println("Создайте пароль для нового сотрудника");
        this.userPassword = scanner.nextLine();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
