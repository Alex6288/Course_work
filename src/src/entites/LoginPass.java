package entites;

import java.io.Serializable;

public class LoginPass implements Serializable {

    private String userPassword;
    private String userName;

    public LoginPass(String userName, String userPassword) {
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
