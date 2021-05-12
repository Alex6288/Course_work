import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

/**
 * ФИО сотрудника, дату рож-
 * дения, пол, контактный телефон, должность, название отдела в котором работает
 * сотрудник, информацию о непосредственном начальнике (если такой есть), дату
 * приема на работу, зарплату.
 */
public class Employee {

    private String firstName;
    private String lastName;
    private String middleName;
    private Date birthday;
    private TypeEmployee typeEmployee;
    private String phoneNumber;
    private String position;
    private TypeWorkDepart nameTypeWorkDepart;
    private Employee chief;
    private Date dateStartWork;
    private BigDecimal salary;

    private String userPassword;
    private String userName;


    public Employee(String firstName,
                    String lastName,
                    String middleName,
                    Date birthday,
                    TypeEmployee typeEmployee,
                    String phoneNumber,
                    String position,
                    TypeWorkDepart nameWorkDepart,
                    Employee chief,
                    Date dateStartWork,
                    BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.typeEmployee = typeEmployee;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.nameTypeWorkDepart = nameWorkDepart;
        this.chief = chief;
        this.dateStartWork = dateStartWork;
        this.salary = salary;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Создайте логин для нового сотрудника");
        this.userName = scanner.nextLine().trim();
        System.out.println("Создайте пароль для нового сотрудника");
        this.userPassword = scanner.nextLine();
    }

    public void setUserNamePass() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Создайте логин для нового сотрудника");
        this.userName = scanner.nextLine().trim();
        System.out.println("Создайте пароль для нового сотрудника");
        this.userPassword = scanner.nextLine();
    }

    public void changeUserName(String newName) {
        this.userName = newName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public TypeEmployee getTypeEmployee() {
        return typeEmployee;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPosition() {
        return position;
    }

    public TypeWorkDepart getNameWorkDepart() {
        return nameTypeWorkDepart;
    }

    public Employee getChief() {
        return chief;
    }

    public Date getDateStartWork() {
        return dateStartWork;
    }

    public BigDecimal getSalary() {
        return salary;
    }
}
