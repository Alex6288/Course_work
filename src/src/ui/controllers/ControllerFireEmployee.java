package ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.EmployeeService;
import ui.Ui;

public class ControllerFireEmployee {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstNameField;

    @FXML
    private Button okButton;

    @FXML
    private TextField middleNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Button cancelButton;

    @FXML
    private Label fireEmployeeLabel;

    @FXML
    void initialize() {
        EmployeeService employeeService = EmployeeService.INSTANCE;

        okButton.setOnAction(actionEvent -> {
            employeeService.fireEmployee(firstNameField.getText(),
                    lastNameField.getText(),
                    middleNameField.getText());
            Ui.openNewWindow(okButton, Ui.PATH_WORKING_SPACE);
        });

        cancelButton.setOnAction(actionEvent -> {
            Ui.openNewWindow(okButton, Ui.PATH_WORKING_SPACE);
        });

    }
}

