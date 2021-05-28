import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class EmployeeAccountingSystem implements IEmployee, IReport, IAccountingSystem {

    private HashMap<String, String> userNamePass;
    private Set<Employee> employees;

    public EmployeeAccountingSystem() {
        this.employees = new HashSet<>();
        this.userNamePass = new HashMap<>();
    }

    @Override
    public boolean checkUser(String userName, String password) {
        return false;
    }

    @Override
    public void getStructureOrganisation() {

    }

    @Override
    public void getAvSalary() {

    }

    @Override
    public void getTopExpensiveEmployee() {

    }

    @Override
    public void getTopDevotedEmployee() {

    }

    @Override
    public void addEmployee(Employee employee) {
        while (userNamePass.containsKey(employee.getUserName())) {
            System.out.println("Такое имя пользователя уже существует, напишите новое");
            employee.setUserNamePass();
        }
        userNamePass.put(employee.getUserName(), employee.getUserPassword());
        employees.add(employee);
    }

    @Override
    public void fireEmployee(Employee employee) {

    }

    @Override
    public void changeEmployeeInfo() {

    }

    @Override
    public Employee findEmployee(String firstName, String lastName, String middleName) {
        for (Employee employee : employees) {
            if (employee.getFirstName().equals(firstName) &&
                    employee.getFirstName().equals(lastName) &&
                    employee.getLastName().equals(lastName)) {
                return employee;
            }
        }
        System.out.println("Такго сотрудника нет");
        return null;
    }

    @Override
    public Set<Employee> findEmployees(String position) {
        Set<Employee> findEmployees = new HashSet<>();
        for (Employee employee : employees) {
            if (employee.getPosition().equals(position)) {
                findEmployees.add(employee);
            }
        }
        return findEmployees;
    }

    @Override
    public Set<Employee> findEmployees(TypeWorkDepart nameTypeWorkDepart) {
        Set<Employee> findEmployees = new HashSet<>();
        for (Employee employee : employees) {
            if (employee.getNameWorkDepart().equals(nameTypeWorkDepart)) {
                findEmployees.add(employee);

            }
        }
        return findEmployees;
    }

    @Override
    public Set<Employee> findEmployees(String firstName, String lastName, String middleName) {
        Set<Employee> findEmployees = new HashSet<>();
        for (Employee employee : employees) {
            if (employee.getChief().getFirstName().equals(firstName) &&
                    employee.getChief().getLastName().equals(lastName) &&
                    employee.getChief().getMiddleName().equals(middleName)) {
                findEmployees.add(employee);

            }
        }
        return findEmployees;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }
}
