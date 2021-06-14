package ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entites.TypePosition;
import entites.TypeWorkDepart;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import services.EmployeeService;
import ui.Ui;

public class ControllerChangeInfoEmployee {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField firstName;

    @FXML
    private Button okButton;

    @FXML
    private TextField middleName;

    @FXML
    private TextField phoneNumberNewField;

    @FXML
    private ChoiceBox<String> nameDepartNewBox;
    private TypeWorkDepart typeWorkDepart;

    @FXML
    private Label positionLabel;

    @FXML
    private Label newDataLabel;

    @FXML
    private TextField lastName;

    @FXML
    private Label nameDepartLabel;

    @FXML
    private ChoiceBox<String> positionNewBox;
    private TypePosition typeNewPosition;

    @FXML
    private Label salaryLabel;

    @FXML
    private TextField salaryNew;

    @FXML
    private Button cancelButton;

    @FXML
    private Label employeeLabel;

    @FXML
    private Label changeInfoEmployeeLabel;

    @FXML
    void initialize() {
        ObservableList<String> typePosition = FXCollections.observableArrayList("Рабочий",
                "Начальник отдела",
                "Заместитель директора",
                "Директор");
        ObservableList<String> typeWorkDepart = FXCollections.observableArrayList("Бухгатерия",
                "Логистика",
                "Реализация");
        ObservableList<String> typeGender = FXCollections.observableArrayList("Мужчина",
                "Женщина");

        positionNewBox.setItems(typePosition);
        positionNewBox.setOnAction(actionEvent -> {
            switch (positionNewBox.getValue()) {
                case "Рабочий" -> {this.typeNewPosition = TypePosition.WORKER;}
                case "Начальник отдела" -> {this.typeNewPosition = TypePosition.CHIEF_DEPART;}
                case "Заместитель директора" -> {this.typeNewPosition = TypePosition.PRE_DIRECTOR;}
                case "Директор" -> {this.typeNewPosition = TypePosition.DIRECTOR;}
            }
        });

        nameDepartNewBox.setItems(typeWorkDepart);
        nameDepartNewBox.setOnAction(actionEvent -> {
            switch (nameDepartNewBox.getValue()) {
                case "Бухгатерия" -> {this.typeWorkDepart = TypeWorkDepart.BOOKKEEPING;}
                case "Логистика" -> {this.typeWorkDepart = TypeWorkDepart.LOGISTICS;}
                case "Реализация" -> {this.typeWorkDepart = TypeWorkDepart.REALIZATION;}
            }
        });

        EmployeeService employeeService = EmployeeService.INSTANCE;

        okButton.setOnAction(actionEvent -> {
            employeeService.changeEmployeeInfo(firstName.getText(),
                    lastName.getText(),
                    middleName.getText(),
                    typeNewPosition,
                    phoneNumberNewField.getText(),
                    Integer.parseInt(salaryNew.getText())
                    );
            Ui.openNewWindowThread(okButton, Ui.PATH_WORKING_SPACE);
        });

        cancelButton.setOnAction(actionEvent -> {
            Ui.openNewWindowThread(okButton, Ui.PATH_WORKING_SPACE);
        });

    }
}
