package services;

/**
 * Интерфейс приложения должен позволять выводить результат работы отчета
 * в консоль или файл. Необходимо предусмотреть возможность входа по логину и
 * паролю.
 */
public interface IAccountingSystem {
    boolean checkUser(String userName, String password);
}
