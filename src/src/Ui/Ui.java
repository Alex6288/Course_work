package Ui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Ui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) throws Exception {

        myStage.setTitle("Система учета сотрудников");


        myStage.show();

    }
}
