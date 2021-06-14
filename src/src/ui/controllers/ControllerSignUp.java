package ui.controllers;

import entites.Employee;
import entites.TypeGender;
import entites.TypePosition;
import entites.TypeWorkDepart;
import errors.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import services.EmployeeService;
import ui.Ui;

import java.time.LocalDate;
import java.util.Locale;

public class ControllerSignUp {

    @FXML
    private TextField firstNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField middleNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private ChoiceBox<String> nameDepartBox;
    private TypeWorkDepart typeWorkDepart;

    @FXML
    private Label positionLabel;

    @FXML
    private ChoiceBox<String> genderBox;
    private TypeGender typeGender;

    @FXML
    private Label genderLabel;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField loginField;

    @FXML
    private Label nameDepartLabel;

    @FXML
    private ChoiceBox<String> positionBox;
    private TypePosition typePosition;

    @FXML
    private Label salaryLabel;

    @FXML
    private TextField salaryField;

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private Button cancelButton;

    @FXML
    private Label errorLabel;

    @FXML
    void initialize() {
        /**
         * Устанавливаем значения ChoiceBox
         */
        ObservableList<String> typePosition = FXCollections.observableArrayList("Рабочий",
                "Начальник отдела",
                "Заместитель директора",
                "Директор");
        ObservableList<String> typeWorkDepart = FXCollections.observableArrayList("Бухгатерия",
                "Логистика",
                "Реализация");
        ObservableList<String> typeGender = FXCollections.observableArrayList("Мужчина",
                "Женщина");

        positionBox.setItems(typePosition);
        positionBox.setOnAction(actionEvent -> {
            switch (positionBox.getValue()) {
                case "Рабочий" -> {
                    this.typePosition = TypePosition.WORKER;
                }
                case "Начальник отдела" -> {
                    this.typePosition = TypePosition.CHIEF_DEPART;
                }
                case "Заместитель директора" -> {
                    this.typePosition = TypePosition.PRE_DIRECTOR;
                }
                case "Директор" -> {
                    this.typePosition = TypePosition.DIRECTOR;
                }
            }
        });

        nameDepartBox.setItems(typeWorkDepart);
        nameDepartBox.setOnAction(actionEvent -> {
            switch (nameDepartBox.getValue()) {
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

        genderBox.setItems(typeGender);
        genderBox.setOnAction(actionEvent -> {
            switch (genderBox.getValue()) {
                case "Мужчина" -> {
                    this.typeGender = TypeGender.MALE;
                }
                case "Женщина" -> {
                    this.typeGender = TypeGender.FEMALE;
                }
            }
        });

        EmployeeService employeeService = EmployeeService.INSTANCE;
        /**
         * Кнопка регистрации
         * СОздает нового сотрудника, присваивает ему логин и пароль, переходит на страницу входа
         */
        signUpButton.setOnAction(actionEvent -> {
            try {
                checkData();
                Employee employee = new Employee(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        middleNameField.getText(),
                        birthdayDatePicker.getValue(),
                        this.typeGender,
                        phoneNumberField.getText(),
                        this.typePosition,
                        this.typeWorkDepart,
                        LocalDate.now(),
                        Integer.parseInt(salaryField.getText()));
                employee.setUserNamePass(loginField.getText(), passwordField.getText());
                employeeService.addEmployee(employee);
                /**
                 * После регистрации переходим на главную страничку
                 */
                Ui.openNewWindowThread(signUpButton, Ui.PATH_USER_LOGIN);
            } catch (UserNameExist userNameExist) {
                errorLabel.setText("Такое имя полльзователя уже существует");
            } catch (EmptyField emptyField) {
                errorLabel.setText("Заполните все поля");
            } catch (PhoneFormatIncorrect phoneFormatIncorrect) {
                errorLabel.setText("Неверный формат телефона");
            } catch (NameIncorrect nameIncorrect) {
                errorLabel.setText("Неверный формат имени");
            } catch (SalaryFormatError salaryFormatError) {
                errorLabel.setText("Неверный формат зарплаты");
            } catch (PassShort passShort){
                errorLabel.setText("Пароль слишком короткий");
            }
        });

        cancelButton.setOnAction(actionEvent -> {
            Ui.openNewWindowThread(signUpButton, Ui.PATH_USER_LOGIN);
        });
    }

    private void checkData() throws NameIncorrect, EmptyField, PhoneFormatIncorrect, SalaryFormatError, PassShort {
        if (!checkPassword(passwordField.getText())) {
            throw new PassShort();
        }
        if (!checkName(firstNameField.getText().trim())) {
            throw new NameIncorrect();
        }
        if (!checkName(lastNameField.getText().trim())) {
            throw new NameIncorrect();
        }
        if (!checkName(middleNameField.getText().trim())) {
            throw new NameIncorrect();
        }
        if (birthdayDatePicker.getValue() == null) {
            throw new EmptyField();
        }
        if (typeGender.getName().isEmpty()) {
            throw new EmptyField();
        }
        if (!checkPhoneNumber(phoneNumberField.getText())) {
            throw new PhoneFormatIncorrect();
        }
        if (typePosition.getName().isEmpty()) {
            throw new EmptyField();
        }
        if (typeWorkDepart.getName().isEmpty()) {
            throw new EmptyField();
        }
        if (!checkSalaryField(salaryField.getText())) {
            throw new SalaryFormatError();
        }
    }

    private boolean checkName(String name) {
        name = name.toLowerCase(Locale.ROOT);
        char[] chars = name.toCharArray();
        if (chars.length < 2) {
            return false;
        }
        for (char c : chars) {
            if (c < 'а' || c > 'я') {
                return false;
            }
        }
        return true;
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != Ui.NUM_PHONE) {
            return false;
        }
        for (char c : phoneNumber.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkSalaryField(String salaryField) {
        for (char c : salaryField.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPassword(String pass) {
        if (pass.length() < 6){
            return false;
        }
        return true;
    }
}


