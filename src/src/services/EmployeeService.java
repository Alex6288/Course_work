package services;

import entites.*;
import repository.FileEmployeesDao;

import java.util.HashSet;
import java.util.Set;

public class EmployeeService implements IEmployee {

    private AccountingSystem accountingSystem;
    private FileEmployeesDao fileEmployeesDao;

    public EmployeeService() {
        this.fileEmployeesDao = new FileEmployeesDao();
        if (fileEmployeesDao.readFromFileEmployeeList().equals(null)) {
            this.accountingSystem = new AccountingSystem();
        } else {
            this.accountingSystem = new AccountingSystem(fileEmployeesDao.readFromFileUserNamePassMap(),
                    fileEmployeesDao.readFromFileEmployeeList()
            );
        }
    }

    private void refreshData(){
        fileEmployeesDao.writeToFileEmployeeList(accountingSystem.getEmployees());
        fileEmployeesDao.writeToFileUserNamePassMap(accountingSystem.getUserNamePass());
    }

    @Override
    public void addEmployee(Employee employee) {
        while (accountingSystem.getUserNamePass().containsKey(employee.getUserName())) {
            System.out.println("Такое имя пользователя уже существует, напишите новое");
            //employee.setUserNamePass(); 
        }
        accountingSystem.getUserNamePass().put(employee.getUserName(), employee.getUserPassword());
        accountingSystem.getEmployees().add(employee);
        refreshData();
    }

    @Override
    public void fireEmployee(String firsName, String lastName, String middleName) {
        accountingSystem.getEmployees().remove(findEmployee(firsName, lastName, middleName));
        refreshData();
    }

    @Override
    public void changeEmployeeInfo(String firsName, String lastName, String middleName,
                                   TypePosition newPosition,
                                   String newPhoneNumber,
                                   Employee newChief,
                                   int newSalary) {
        Employee newEmployee = findEmployee(firsName, lastName, middleName);
        newEmployee.setChief(newChief);
        newEmployee.setPhoneNumber(newPhoneNumber);
        newEmployee.setSalary(newSalary);
        newEmployee.setPosition(newPosition);
        refreshData();
    }

    @Override
    public Employee findEmployee(String firstName, String lastName, String middleName) {
        for (Employee employee : accountingSystem.getEmployees()) {
            if (employee.getFirstName().equals(firstName) &&
                    employee.getFirstName().equals(lastName) &&
                    employee.getLastName().equals(lastName)) {
                return employee;
            }
        }
        System.out.println("Такого сотрудника нет" + firstName + " " + lastName);
        return null;
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

    public Set<Employee> getEmployees() {
        return accountingSystem.getEmployees();
    }

}
