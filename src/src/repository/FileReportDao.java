package repository;

import entites.Employee;
import services.ReportService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileReportDao {

    public static final FileReportDao INSTANCE = new FileReportDao();

    private ReportService reportService;

    private static final String FILE_NAME_REPORT = "report.txt";
    private final String FILE_NAME_EMPLOYEE_REPORT = "employee_report.txt";

    private File reportFile;
    private File fileEmployeeReport;

    private FileReportDao() {
        this.reportService = ReportService.INSTANCE;

        this.fileEmployeeReport = new File(FILE_NAME_EMPLOYEE_REPORT);
        this.reportFile = new File(FILE_NAME_REPORT);
        try {
            if (!reportFile.exists()) {
                reportFile.createNewFile();
            }
            if (!fileEmployeeReport.exists()) {
                fileEmployeeReport.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Записывает полученные строки в файл отчета
     *
     * @param stringList колекция строк для записи в файл отчета
     */
    private void saveReportToFile(List<String> stringList) {
        cleanReportFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile, true));
             PrintWriter pw = new PrintWriter(bw)) {
            stringList.forEach(pw::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveReportToFile(String s) {
        cleanReportFile();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reportFile, true));
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отчищает файл отчета
     */
    private void cleanReportFile() {
        try {
            Files.newBufferedWriter(Path.of(FILE_NAME_REPORT), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Сохраняет в файл отчета структуру организации
     */
    public void saveToFileStructureOrganisation(List<String> strings) {
        saveReportToFile(strings);
    }

    /**
     * Сохраняет в файл отчета среднюю зарплату по организации
     */
    public void saveToFileAvSalary(String str) {
        saveReportToFile("Средняя зарплата по организации : " + str);
    }

    /**
     * Сохраняет в файл отчета среднюю зарплату по департаменту организации
     */
    public void saveToFileAvSalaryDepart(String str) {
        saveReportToFile("Средняя зарплата по департаменту " + str);
    }

    /**
     * Сохраняет в файл отчета список самых дорогих сотрудников организации
     */
    public void saveToFileTopExpensiveEmployee(List<String> listEmployee) {
        saveReportToFile(listEmployee);
    }

    /**
     * Сохраняет в файл отчета список самых преданных сотрудников организации
     */
    public void saveInFileTopDevotedEmployee(List<String> topDevotedEmployee) {
        saveReportToFile(topDevotedEmployee);
    }

    /**
     * Отчеты по сотрудникам
     */

    /**
     * Отчищает файл отчета
     */
    private void cleanEmployeeReportFile() {
        try {
            Files.newBufferedWriter(Path.of(FILE_NAME_EMPLOYEE_REPORT), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param employee обьект сотрудник который записываем в файл отчета
     */
    private void saveToFileEmployeeReport(Employee employee) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileEmployeeReport, true));
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(employee.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Сохраняем в файл отчета данные о сотруднике найденом по полному имени
     */

    /**
     * Cохраняет в файл сотрудников
     */

    public void saveToFileFindEmployee(Employee employee){
        cleanEmployeeReportFile();
        saveReportToFile(employee.toString());
    }
    /**
     * Сохраняем в файл отчета данные о сотрудниках
     */
    public void saveToFileFindEmployees(Set<Employee> employees){
        cleanEmployeeReportFile();
        List<String> strings = Collections.singletonList(employees.toString());
        saveReportToFile(strings);
    }
}
