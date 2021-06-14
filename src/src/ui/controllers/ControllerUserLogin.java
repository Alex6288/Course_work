package ui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.FileEmployeesDao;
import ui.Ui;

public class ControllerUserLogin {

    private FileEmployeesDao fileEmployeesDao;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button authSignButton;

    @FXML
    private Button loginSignupButton;

    @FXML
    private Label errorLabel;

    @FXML
    void initialize() {

        fileEmployeesDao = FileEmployeesDao.INSTANCE;

        /**
         * Кнопка "Зарегестрироваться"
         * При нажатии переходим на окно регистрации нового пользователя
         */
        loginSignupButton.setOnAction(actionEvent -> {
            Ui.openNewWindow(loginSignupButton, Ui.PATH_SIGN_UP);
        });

        authSignButton.setOnAction(actionEvent -> {
            HashMap<String, String> namePass = fileEmployeesDao.readFromFileUserNamePassMap();
            String login = loginField.getText();
            String pass = passwordField.getText();
            if (namePass.containsKey(loginField.getText())) {
                if (namePass.get(login).equals(pass)) {
                    Ui.openNewWindowThread(authSignButton, Ui.PATH_WORKING_SPACE);
                }
            }
            errorLabel.setText("Неверный логин или пароль");
        });
    }
}
