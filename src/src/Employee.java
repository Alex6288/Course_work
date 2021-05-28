import java.io.Serializable;
import java.util.Date;

/**
 * ФИО сотрудника, дату рож-
 * дения, пол, контактный телефон, должность, название отдела в котором работает
 * сотрудник, информацию о непосредственном начальнике (если такой есть), дату
 * приема на работу, зарплату.
 */
public class Employee implements Serializable {

    private String firstName;
    private String lastName;
    private String middleName;
    private java.sql.Date birthday;
    private TypeGender gender;
    private String phoneNumber;
    private TypePosition position;
    private TypeWorkDepart nameTypeWorkDepart;
    private Employee chief;
    private Date dateStartWork;
    private int salary;

    private Ui userNamePass;

    public Employee(String firstName,
                    String lastName,
                    String middleName,
                    java.sql.Date birthday,
                    TypeGender gender,
                    String phoneNumber,
                    TypePosition position,
                    TypeWorkDepart nameWorkDepart,
                    Employee chief,
                    Date dateStartWork,
                    int salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.birthday = birthday;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.nameTypeWorkDepart = nameWorkDepart;
        this.chief = chief;
        this.dateStartWork = dateStartWork;
        this.salary = salary;
        setUserNamePass();
    }

    public void setUserNamePass() {
        this.userNamePass = new Ui();
    }

    public void changeUserName(String newName) {
        this.userNamePass.setUserName(newName);
    }

    public String getUserName() {
        return userNamePass.getUserName();
    }

    public String getUserPassword() {
        return userNamePass.getUserPassword();
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

    public TypeGender getGender() {
        return gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public TypePosition getPosition() {
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

    public int getSalary() {
        return salary;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPosition(TypePosition position) {
        this.position = position;
    }

    public void setChief(Employee chief) {
        this.chief = chief;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getFullName(){
        return firstName + " " + lastName + " " + middleName;
    }

    @Override
    public String toString() {
        String s = chief == null ? "нет начальника" : (chief.getFirstName() + " " + chief.getLastName() + " " + chief.getMiddleName());
        return "Employee{" +
                "firstName='" + firstName + '\'' + "\n" +
                ", lastName='" + lastName + '\'' + "\n" +
                ", middleName='" + middleName + '\'' + "\n" +
                ", birthday=" + birthday + "\n" +
                ", gender=" + gender + "\n" +
                ", phoneNumber='" + phoneNumber + '\'' + "\n" +
                ", position=" + position + "\n" +
                ", nameTypeWorkDepart=" + nameTypeWorkDepart + "\n" +
                ", chief=" + s + "\n" +
                ", dateStartWork=" + dateStartWork + "\n" +
                ", salary=" + salary + "\n" +
                '}';
    }
}
