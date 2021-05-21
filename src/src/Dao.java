import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dao implements IEmployee, IReport, IAccountingSystem {

    private final int NUM_EMPLOYEES_FOR_REPORT = 10;

    private final String FILE_NAME_USERS_PASSWORD_NAMES = "name_pass_users.txt";
    private final String FILE_NAME_EMPLOYEE_LIST = "employee_list.bin";
    private final String FILE_NAME_REPORT = "report.txt";

    private HashMap<String, String> userNamePass;
    private Set<Employee> employees;
    private File fileUsersNamePass;
    private File employeeList;
    private File reportFile;

    public Dao() {
        this.employees = new HashSet<>();
        this.userNamePass = new HashMap<>();
        fileUsersNamePass = new File(FILE_NAME_USERS_PASSWORD_NAMES);
        employeeList = new File(FILE_NAME_EMPLOYEE_LIST);
        reportFile = new File(FILE_NAME_REPORT);
        try {
            if (!fileUsersNamePass.exists()) {
                fileUsersNamePass.createNewFile();
            }
            if (!employeeList.exists()) {
                employeeList.createNewFile();
            }
            if (!reportFile.exists()) {
                reportFile.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkUser(String userName, String password) {
        //todo реализовать вход по паролю
        return false;
    }

    @Override
    public void getStructureOrganisation(boolean inFile) {
        cleanReportFile();
        for (TypeWorkDepart depart : TypeWorkDepart.values()) {
            printReport(inFile, "Имя отдела :" + depart.toString());
            printReport(inFile, "Количество сотрудников в отделе : " + findEmployees(depart).size());
            printReport(inFile, "Имя начальника : " + getChiefName(depart));
            printReport(inFile, "Сотрудники :");
            for (Employee employee : findEmployees(depart)) {
                printReport(inFile, employee.getFullName());
            }
            printReport(inFile, "Средняя зарплата по отделу : " + getAvSalary(depart));
        }
        printReport(inFile, "Средняя зарплата по организации : " + getAvSalary());
    }

    private void cleanReportFile() {
        try {
            Files.newBufferedWriter(Path.of(FILE_NAME_REPORT), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getChiefName(TypeWorkDepart depart) {
        ArrayList<Employee> chief = findEmployees(TypePosition.CHIEF).stream()
                .takeWhile(employee -> employee.getNameWorkDepart().equals(depart))
                .collect(Collectors.toCollection(ArrayList::new));
        //todo подумать как взять одного начальника из потока и заменить строку нижу
        return chief.size() == 0 ? "нет начальника" : chief.get(0).getFullName();
    }

    private void printReport(boolean inFile, String s) {
        if (inFile) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME_REPORT, true));
                 PrintWriter pw = new PrintWriter(bw)) {
                pw.println(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(s);
        }

    }

    @Override
    public BigDecimal getAvSalary() {
        BigDecimal sum = new BigDecimal(0);
        for (TypeWorkDepart depart : TypeWorkDepart.values()) {
            sum = sum.add(getAvSalary(depart));
        }
        return sum.divide(BigDecimal.valueOf(TypeWorkDepart.values().length), RoundingMode.UP);
    }

    public BigDecimal getAvSalary(TypeWorkDepart depart) {
        int sum = 0;
        for (Employee employee : findEmployees(depart)) {
            sum += employee.getSalary();
        }
        BigDecimal av = BigDecimal.valueOf(sum);
        return av.divide(BigDecimal.valueOf(findEmployees(depart).size() == 0 ? 1 : findEmployees(depart).size()),
                RoundingMode.UP);
    }

    @Override
    public void getTopExpensiveEmployee() {
        employees.stream()
                .sorted((o1, o2) -> Integer.compare(o2.getSalary(), o1.getSalary()))
                .limit(NUM_EMPLOYEES_FOR_REPORT)
                .forEach(employee -> System.out.println(employee.getFullName() + " зп = " + employee.getSalary()));
    }

    @Override
    public void getTopDevotedEmployee() {
        employees.stream()
                .sorted(Comparator.comparing(Employee::getDateStartWork))
                .limit(NUM_EMPLOYEES_FOR_REPORT)
                .forEach(employee -> System.out.println(employee.getFullName() +
                        " start = " + employee.getDateStartWork()));
    }

    @Override
    public void addEmployee(Employee employee) {
        while (userNamePass.containsKey(employee.getUserName())) {
            System.out.println("Такое имя пользователя уже существует, напишите новое");
            employee.setUserNamePass();
        }
        userNamePass.put(employee.getUserName(), employee.getUserPassword());
        writeUserLoginPass(employee);
        employees.add(employee);
        writeEmployees();
    }

    @Override
    public void fireEmployee(String firsName, String lastName, String middleName) {
        employees.remove(findEmployee(firsName, lastName, middleName));
        writeEmployees();
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
        System.out.println("Такого сотрудника нет" + firstName + " " + lastName);
        return null;
    }

    @Override
    public Set<Employee> findEmployees(TypePosition position) {
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

    private void writeEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME_EMPLOYEE_LIST))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeUserLoginPass(Employee employee) {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME_USERS_PASSWORD_NAMES))) {
            oos.writeObject(userNamePass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUserLoginPass(Employee employee) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME_USERS_PASSWORD_NAMES))) {
            userNamePass.remove(employee.getUserName());
            oos.writeObject(userNamePass);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}