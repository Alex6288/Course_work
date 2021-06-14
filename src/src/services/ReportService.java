package services;

import entites.Employee;
import entites.TypePosition;
import entites.TypeWorkDepart;
import errors.FullNameExpected;
import repository.FileEmployeesDao;
import repository.FileReportDao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ReportService implements IReportService {

    public static final ReportService INSTANCE = new ReportService();

    private final int NUM_EMPLOYEES_FOR_REPORT = 10;

    private EmployeeService employeeService;

    private FileReportDao fileReportDao;
    private FileEmployeesDao fileEmployeesDao;

    private ReportService() {
        this.employeeService = EmployeeService.INSTANCE;
        this.fileEmployeesDao = FileEmployeesDao.INSTANCE;
        this.fileReportDao = FileReportDao.INSTANCE;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    /**
     * Методы сохранения в файл отчета различной информации
     */
    public void safeToFileStructureOrganisation(){
        fileReportDao.saveToFileStructureOrganisation(getStructureOrganisation());
    }

    public void saveToFileAvSalary(){
        fileReportDao.saveToFileAvSalary(getAvSalary().toString());
    }

    public void saveToFileAvSalaryDepart(TypeWorkDepart depart){
        fileReportDao.saveToFileAvSalaryDepart(getAvSalary(depart).toString());
    }

    public void saveToFileTopDevotedEmployee(){
        fileReportDao.saveInFileTopDevotedEmployee(getTopDevotedEmployee());
    }

    /**
     * Методы сохранения в файл отчета информации о найденых сотрудниках по запросу
     */
    public void saveToFileTopExpensiveEmployee(){
        fileReportDao.saveToFileTopExpensiveEmployee(getTopExpensiveEmployee());
    }

    public void saveToFileFindEmployeeFullName(String firstName, String lastName, String middleName){
        fileReportDao.saveToFileFindEmployee(employeeService.findEmployee(firstName, lastName, middleName));
    }

    public void saveToFileFindEmployeeFullName(String fullName) throws FullNameExpected {
        fileReportDao.saveToFileFindEmployee(employeeService.findEmployee(fullName));
    }

    public void saveToFileFindEmployeesNameWorkDepart(TypeWorkDepart depart){
        fileReportDao.saveToFileFindEmployees(employeeService.findEmployees(depart));
    }

    public void saveToFileFindEmployeePosition(TypePosition position){
        fileReportDao.saveToFileFindEmployees(employeeService.findEmployees(position));
    }

    public void saveToFileFindEmployeesChiefName(String firstName, String lastName, String middleName){
        fileReportDao.saveToFileFindEmployees(employeeService.findEmployees(firstName, lastName, middleName));
    }

    public void saveToFileFindEmployeesChiefName(String fullName) throws FullNameExpected {
        fileReportDao.saveToFileFindEmployees(employeeService.findEmployees(fullName));
    }

    /**
     * Составляет структуру организцаа по отделам
     * @return возвращает лист строк отчета
     */
    @Override
    public List<String> getStructureOrganisation() {
        List<String> report = new ArrayList<>();
        for (TypeWorkDepart depart : TypeWorkDepart.values()) {
            report.add("Имя отдела :" + depart.getName() + "\n");
            report.add("Количество сотрудников в отделе : " + employeeService.findEmployees(depart).size() + "\n");
            report.add("Имя начальника : " + getChiefName(depart) + "\n");
            report.add("Сотрудники :" + "\n");
            for (Employee employee : employeeService.findEmployees(depart)) {
                report.add( employee.getFullName() + "\n");
            }
            report.add( "Средняя зарплата по отделу : " + getAvSalary(depart) + "\n");
        }
        report.add( "Средняя зарплата по организации : " + getAvSalary() + "\n");
        report.add(" " + "\n");
        return report;
    }

    /**
     * @param depart название департамента где числется сотрудник
     * @return возвращает полное имя начальника департамента
     */
    private String getChiefName(TypeWorkDepart depart) {
        ArrayList<Employee> chief = employeeService.findEmployees(TypePosition.CHIEF_DEPART).stream()
                .takeWhile(employee -> employee.getNameWorkDepart().equals(depart))
                .collect(Collectors.toCollection(ArrayList::new));
        return chief.size() == 0 ? "нет начальника" : chief.get(0).getFullName();
    }

    /**
     * @return возвращает среднюю зарплату по всей организации
     */
    @Override
    public BigDecimal getAvSalary() {
        BigDecimal sum = new BigDecimal(0);
        for (TypeWorkDepart depart : TypeWorkDepart.values()) {
            sum = sum.add(getAvSalary(depart));
        }
        return sum.divide(BigDecimal.valueOf(TypeWorkDepart.values().length), RoundingMode.UP);
    }

    /**
     * @return возвращает среднюю зарплату по одному департаменту организации
     */
    public BigDecimal getAvSalary(TypeWorkDepart depart) {
        int sum = 0;
        for (Employee employee : employeeService.findEmployees(depart)) {
            sum += employee.getSalary();
        }
        BigDecimal av = BigDecimal.valueOf(sum);
        return av.divide(BigDecimal.valueOf(employeeService.findEmployees(depart).size() == 0 ?
                        1 : employeeService.findEmployees(depart).size()),
                RoundingMode.UP);
    }

    /**
     * @return возвращает лист самых дорогих сотрудников по зарплате
     */
    @Override
    public List<String> getTopExpensiveEmployee() {
        List<String> report = new ArrayList<>();
        employeeService.getEmployees().stream()
                .sorted((o1, o2) -> Integer.compare(o2.getSalary(), o1.getSalary()))
                .limit(NUM_EMPLOYEES_FOR_REPORT)
                .forEach(employee -> report.add(employee.getFullName() + " зп = " + employee.getSalary()));
        return report;
    }

    /**
     * @return возвращает лист самых преданных сотрудников
     */
    @Override
    public List<String> getTopDevotedEmployee() {
        List<String> report = new ArrayList<>();
        employeeService.getEmployees().stream()
                .sorted(Comparator.comparing(Employee::getDateStartWork))
                .limit(NUM_EMPLOYEES_FOR_REPORT)
                .forEach(employee -> report.add(employee.getFullName() +
                        " начало работы : " + employee.getDateStartWork()));
        return report;
    }
}
