package ui.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import entites.TypePosition;
import entites.TypeWorkDepart;
import errors.FullNameExpected;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.ReportService;
import ui.Ui;

public class ControllerWorkingSpace {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label errorLabel;

    @FXML
    private TextArea reportConsoleTextArea;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button fireEmployeeButton;

    @FXML
    private Button chengeInfoEmployeeButton;

    @FXML
    private Button reportStructureButton;

    @FXML
    private Label reportLabel;

    @FXML
    private Label employeesLabel;

    @FXML
    private CheckBox fileSaveCheckBox;

    @FXML
    private CheckBox consoleSaveCheckBox;

    @FXML
    private Label saveInfoLabel;

    @FXML
    private Button reportAvSalaryCompButton;

    @FXML
    private Button reportAvSalaryDepartButton;

    @FXML
    private Button reportExpensiveEmployeeButton;

    @FXML
    private Button reportTopDevotedEmployeeButton;

    @FXML
    private Button findEmployeeButton;

    @FXML
    private ChoiceBox<String> nameDepartChoiceBox;
    private TypeWorkDepart typeWorkDepart;

    @FXML
    private ChoiceBox<String> findChoiceBox;

    @FXML
    private Button authOutButton;

    @FXML
    private Label userFullNameLabel;

    @FXML
    private ChoiceBox<String> typePositionChoiceBox;
    private TypePosition typePosition;

    @FXML
    private TextField findFullNameField;

    @FXML
    private Label findPositionLabel;

    @FXML
    private Label findFullNameLabel;

    @FXML
    void initialize() {


        ReportService reportService = ReportService.INSTANCE;
        userFullNameLabel.setText("");

        ObservableList<String> typeWorkDepartList = FXCollections.observableArrayList("Бухгатерия",
                "Логистика",
                "Реализация");

        nameDepartChoiceBox.setItems(typeWorkDepartList);
        nameDepartChoiceBox.setOnAction(actionEvent -> {
            switch (nameDepartChoiceBox.getValue()) {
                case "Бухгатерия" -> {
                    this.typeWorkDepart = TypeWorkDepart.BOOKKEEPING;
                }
                case "Логистика" -> {
                    this.typeWorkDepart = TypeWorkDepart.LOGISTICS;
                }
                case "Реализация" -> {
                    this.typeWorkDepart = TypeWorkDepart.REALIZATION;
                }
            }
        });

        ObservableList<String> typePositionList = FXCollections.observableArrayList("Рабочий",
                "Начальник отдела",
                "Заместитель директора",
                "Директор");

        typePositionChoiceBox.setItems(typePositionList);
        typePositionChoiceBox.setOnAction(actionEvent -> {
            switch (typePositionChoiceBox.getValue()) {
                case "Рабочий" -> {this.typePosition = TypePosition.WORKER;}
                case "Начальник отдела" -> {this.typePosition = TypePosition.CHIEF_DEPART;}
                case "Заместитель директора" -> {this.typePosition = TypePosition.PRE_DIRECTOR;}
                case "Директор" -> {this.typePosition = TypePosition.DIRECTOR;}
            }
        });

        ObservableList<String> typeFindEmployee = FXCollections.observableArrayList("По полному имени начальника",
                "По названию отдела",
                "Полному имени сотрудника",
                "По должности");
        findChoiceBox.setItems(typeFindEmployee);

        addEmployeeButton.setOnAction(actionEvent -> {
            Ui.openNewWindowThread(addEmployeeButton, Ui.PATH_ADD_EMPLOYEE);
        });

        authOutButton.setOnAction(actionEvent -> {
            Ui.openNewWindowThread(authOutButton, Ui.PATH_USER_LOGIN);
        });

        chengeInfoEmployeeButton.setOnAction(actionEvent -> {
            Ui.openNewWindowThread(chengeInfoEmployeeButton, Ui.PATH_CHANGE_INFO_EMPLOYEE);
        });

        fireEmployeeButton.setOnAction(actionEvent -> {
            Ui.openNewWindowThread(fireEmployeeButton, Ui.PATH_FIRE_EMPLOYEE);
        });

        reportStructureButton.setOnAction(actionEvent -> {
                    if (fileSaveCheckBox.isSelected()) {
                        reportService.safeToFileStructureOrganisation();
                    }
                    if (consoleSaveCheckBox.isSelected()) {
                        reportConsoleTextArea.setText(reportService.getStructureOrganisation().toString());
                    }
                }
        );

        reportAvSalaryCompButton.setOnAction(actionEvent -> {
            if (fileSaveCheckBox.isSelected()) {
                reportService.saveToFileAvSalary();
            }
            if (consoleSaveCheckBox.isSelected()) {
                reportConsoleTextArea.setText("Средняя зарплата по организации : \n" +
                        reportService.getAvSalary().toString());
            }

        });

        reportAvSalaryDepartButton.setOnAction(actionEvent -> {
            if (fileSaveCheckBox.isSelected()) {
                reportService.saveToFileAvSalaryDepart(typeWorkDepart);
            }
            if (consoleSaveCheckBox.isSelected()) {
                reportConsoleTextArea.setText("Средняя зарплата по отдуле : " + typeWorkDepart.getName() + "\n" +
                        reportService.getAvSalary(typeWorkDepart));
            }
        });

        reportExpensiveEmployeeButton.setOnAction(actionEvent -> {
            if (fileSaveCheckBox.isSelected()) {
                reportService.saveToFileTopExpensiveEmployee();
            }
            if (consoleSaveCheckBox.isSelected()) {
                reportConsoleTextArea.setText("Самые дорогие сотрудники компании : \n" +
                        reportService.getTopExpensiveEmployee().toString());
            }
        });

        reportTopDevotedEmployeeButton.setOnAction(actionEvent -> {
                    if (fileSaveCheckBox.isSelected()) {
                        reportService.saveToFileTopDevotedEmployee();
                    }
                    if (consoleSaveCheckBox.isSelected()) {
                        reportConsoleTextArea.setText("Самые дорогие сотрудники компании : \n" +
                                reportService.getTopDevotedEmployee().toString());
                    }
                }
        );

        findEmployeeButton.setOnAction(actionEvent -> {
            System.out.println("Поиск");
            if (fileSaveCheckBox.isSelected()) {
                switch (findChoiceBox.getValue()) {
                    case "По полному имени начальника" -> {
                        try {
                            reportService.saveToFileFindEmployeesChiefName(findFullNameField.getText());
                        } catch (FullNameExpected fullNameExpected) {
                            errorLabel.setText("Введено не полное имя");
                        }
                    }
                    case "По названию отдела" -> {reportService.saveToFileFindEmployeesNameWorkDepart(typeWorkDepart);}
                    case "Полному имени сотрудника" -> {
                        try {
                            reportService.saveToFileFindEmployeeFullName(findFullNameField.getText());
                        } catch (FullNameExpected fullNameExpected) {
                            errorLabel.setText("Введено не полное имя");
                        }
                    }
                    case "По должности" -> {reportService.saveToFileFindEmployeePosition(typePosition);}
                }
            }
            if (consoleSaveCheckBox.isSelected()) {
                System.out.println("Консоль");
                switch (findChoiceBox.getValue()) {
                    case "По полному имени начальника" -> {
                        try {
                            reportConsoleTextArea.setText(reportService.getEmployeeService()
                                    .findEmployees(findFullNameField.getText()).toString());
                        } catch (FullNameExpected fullNameExpected) {
                            errorLabel.setText("Введено не полное имя");
                        }
                    }
                    case "По названию отдела" -> {
                        reportConsoleTextArea.setText(reportService.getEmployeeService()
                                .findEmployees(typeWorkDepart).toString());
                    }
                    case "Полному имени сотрудника" -> {
                        try {
                            reportConsoleTextArea.setText(reportService.getEmployeeService()
                                    .findEmployee(findFullNameField.getText()).toString());
                        } catch (FullNameExpected fullNameExpected) {
                            errorLabel.setText("Введено не полное имя");
                        }
                    }
                    case "По должности" -> {
                        reportConsoleTextArea.setText(reportService.getEmployeeService()
                                .findEmployees(typePosition).toString());
                    }
                }
            }
        });

        // findChoiceBox.getValue()
    }

}
