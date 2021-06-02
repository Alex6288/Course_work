package entites;

import java.io.Serializable;

public class Ui implements Serializable {

    private String userPassword;
    private String userName;

    public Ui(String userPassword, String userName) {
        this.userPassword = userPassword;
        this.userName = userName;
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
