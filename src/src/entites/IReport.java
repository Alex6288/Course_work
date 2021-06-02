package entites;

import java.math.BigDecimal;
import java.util.List;

/**
 * При-
 *  * ложение должно иметь возможность создавать следующие отчеты: структура ор-
 *  * ганизации (информация об отделах, ФИО начальников отделов), средняя зарплата
 *  * по организации и по отделам, ТОП-10 самых дорогих сотрудников по зарплате,
 *  * ТОП-10 самых преданных сотрудников по количеству лет работы в организации.
 *  * Приложение должно сохранять и загружать информацию об организации из
 *  * файлов.
 */
public interface IReport {
    List<String> getStructureOrganisation();
    BigDecimal getAvSalary();
    List<String> getTopExpensiveEmployee();
    List<String> getTopDevotedEmployee();
}
