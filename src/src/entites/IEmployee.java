package entites;

import entites.Employee;
import entites.TypePosition;
import entites.TypeWorkDepart;

import java.util.Set;

/**
 * Приложение должно позволять: принимать на работу новых сотрудников,
 * увольнять сотрудников, изменять информацию о сотрудниках.
 * Приложение долж-
 * но предоставить функциональность по поиску сотрудников внутри организации
 * по таким параметрам: ФИО, должности, названию отдела, ФИО начальника.
 * При-
 * ложение должно иметь возможность создавать следующие отчеты: структура ор-
 * ганизации (информация об отделах, ФИО начальников отделов), средняя зарплата
 * по организации и по отделам, ТОП-10 самых дорогих сотрудников по зарплате,
 * ТОП-10 самых преданных сотрудников по количеству лет работы в организации.
 * Приложение должно сохранять и загружать информацию об организации из
 * файлов.
 */
public interface IEmployee {
    void addEmployee(Employee employee);
    void fireEmployee(String firsName,String lastName, String middleName);
    void changeEmployeeInfo(String firsName, String lastName, String middleName,
                            TypePosition newPosition,
                            String newPhoneNumber,
                            Employee newChief,
                            int newSalary);
    Employee findEmployee(String firstName, String lastName, String middleName);
    Set<Employee> findEmployees(TypePosition position);
    Set<Employee> findEmployees(TypeWorkDepart nameTypeWorkDepart);
    Set<Employee> findEmployees(String firstName, String lastName, String middleName);
}
