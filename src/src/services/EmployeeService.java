package services;

import entites.*;
import errors.FullNameExpected;
import errors.UserNameExist;
import repository.FileEmployeesDao;

import java.util.HashSet;
import java.util.Set;

public class EmployeeService implements IEmployeeService {

    private AccountingSystem accountingSystem;
    private FileEmployeesDao fileEmployeesDao;

    public static final EmployeeService INSTANCE = new EmployeeService();

    private EmployeeService() {
        this.fileEmployeesDao = FileEmployeesDao.INSTANCE;
        this.accountingSystem = AccountingSystem.INSTANCE;
        if (fileEmployeesDao.readFromFileEmployeeList() == null) {
            System.out.println("Файл данных в сотрудниках пустой");
            this.accountingSystem = AccountingSystem.INSTANCE;
        } else {
            this.accountingSystem.loadData(fileEmployeesDao.readFromFileUserNamePassMap(),
                    fileEmployeesDao.readFromFileEmployeeList()
            );
        }
    }

    private void refreshData(){
        fileEmployeesDao.writeToFileEmployeeList(accountingSystem.getEmployees());
        fileEmployeesDao.writeToFileUserNamePassMap(accountingSystem.getUserNamePass());
    }

    @Override
    public void addEmployee(Employee employee) throws UserNameExist {
        while (accountingSystem.getUserNamePass().containsKey(employee.getUserName())) {
            System.out.println("Такое имя пользователя уже существует, напишите новое");
            throw new UserNameExist();
        }
        accountingSystem.getUserNamePass().put(employee.getUserName(), employee.getUserPassword());
        accountingSystem.getEmployees().add(setChief(employee));
        refreshData();
    }

    /**
     * Метод устанавливает начальника в организации в зависимости от позиции
     * У каждой должности свой приоритет
     * @param empl новый сотрудник сотрудкик
     */
    private Employee setChief(Employee empl){
            for (Employee employee : accountingSystem.getEmployees()) {
                if (employee.getNameWorkDepart().equals(empl.getNameWorkDepart())) {
                    if (employee.getPosition().getPriority() == empl.getPosition().getPriority() - 1) {
                        employee.setChief(empl);
                    }
                    if (empl.getPosition().getPriority() + 1 == employee.getPosition().getPriority()) {
                        empl.setChief(employee);
                    }
                }
            }
            return empl;

    }


    @Override
    public void fireEmployee(String firsName, String lastName, String middleName) {
        if (findEmployee(firsName, lastName, middleName) != null) {
            Employee fireEmployee = findEmployee(firsName, lastName, middleName);
            accountingSystem.getUserNamePass().remove(fireEmployee.getUserName());
            accountingSystem.getEmployees().remove(fireEmployee);
            refreshData();
        }
    }

    @Override
    public void changeEmployeeInfo(String firsName, String lastName, String middleName,
                                   TypePosition newPosition,
                                   String newPhoneNumber,
                                   int newSalary) {
        Employee newEmployee = findEmployee(firsName, lastName, middleName);
        newEmployee.setPhoneNumber(newPhoneNumber);
        newEmployee.setSalary(newSalary);
        newEmployee.setPosition(newPosition);
        refreshData();
    }

    @Override
    public Employee findEmployee(String firstName, String lastName, String middleName) {
        accountingSystem.getEmployees().forEach(System.out::println);
        System.out.println();
        for (Employee employee : accountingSystem.getEmployees()) {
            if (employee.getFirstName().equals(firstName) &&
                    employee.getLastName().equals(lastName) &&
                    employee.getMiddleName().equals(middleName)) {
                return employee;
            }
        }
        System.out.println("Такого сотрудника нет " + firstName + " " + lastName);
        return null;
    }

    public Employee findEmployee(String fullName) throws FullNameExpected {
        String[] names = fullName.split("\s+");
        if (names.length < 3) {
            throw new FullNameExpected();
        }
        System.out.println(fullName);
        System.out.println(names[0] + " " + names[1] + " " +  names[2]);
        return findEmployee(names[0], names[1], names[2]);
    }

    @Override
    public Set<Employee> findEmployees(TypePosition position) {
        Set<Employee> findEmployees = new HashSet<>();
        for (Employee employee : accountingSystem.getEmployees()) {
            if (employee.getPosition().equals(position)) {
                findEmployees.add(employee);
            }
        }
        return findEmployees;
    }

    @Override
    public Set<Employee> findEmployees(TypeWorkDepart nameTypeWorkDepart) {
        Set<Employee> findEmployees = new HashSet<>();
        for (Employee employee : accountingSystem.getEmployees()) {
            if (employee.getNameWorkDepart().equals(nameTypeWorkDepart)) {
                findEmployees.add(employee);
            }
        }
        return findEmployees;
    }

    @Override
    public Set<Employee> findEmployees(String firstName, String lastName, String middleName) {
        Set<Employee> findEmployees = new HashSet<>();
        for (Employee employee : accountingSystem.getEmployees()) {
            if (employee.getChief().getFirstName().equals(firstName) &&
                    employee.getChief().getLastName().equals(lastName) &&
                    employee.getChief().getMiddleName().equals(middleName)) {
                findEmployees.add(employee);
            }
        }
        return findEmployees;
    }

    public Set<Employee> findEmployees(String fullName) throws FullNameExpected {
        String[] names = fullName.split("\s+");
        if (names.length < 3) {
            throw new FullNameExpected();
        }
        return findEmployees(names[0], names[1], names[2]);
    }

    public Set<Employee> getEmployees() {
        return accountingSystem.getEmployees();
    }

}
