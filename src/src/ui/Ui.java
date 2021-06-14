package ui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Ui extends Application {

    public static final int NUM_PHONE = 11;

    public static final String PATH_ADD_EMPLOYEE = "view/Addemployee.fxml";
    public static final String PATH_CHANGE_INFO_EMPLOYEE = "view/ChangeInfoEmployee.fxml";
    public static final String PATH_FIRE_EMPLOYEE = "view/FireEmployee.fxml";
    public static final String PATH_USER_LOGIN = "view/UserLogin.fxml";
    public static final String PATH_SIGN_UP = "view/UserSignUp.fxml";
    public static final String PATH_WORKING_SPACE = "view/WorkingSpace.fxml";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/UserLogin.fxml"));
        myStage.setTitle("Система учета сотрудников");
        Scene scene = new Scene(root, 815, 859);
        myStage.setScene(scene);
        myStage.show();

    }

    public static void openNewWindow(Button button, String nameFxmlFile) {
        //Закрываем окно при нажатии и открываем новое
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
        //Открываем новое окно
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Ui.class.getResource(nameFxmlFile));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.showAndWait();
    }

    public static void openNewWindowThread(Button button, String nameFxmlFile) {
        NewWindowThread newWindowThread = new NewWindowThread(button, nameFxmlFile);
        newWindowThread.start();
    }
}


