package repository;

import entites.Employee;

import java.io.*;
import java.util.HashMap;
import java.util.Set;

public class FileEmployeesDao {

    private final String FILE_NAME_USERS_PASSWORD_NAMES = "name_pass_users.txt";
    private final String FILE_NAME_EMPLOYEE_LIST = "employee_list.bin";

    private File fileUsersNamePass;
    private File fileEmployeeList;

    public FileEmployeesDao() {
        fileUsersNamePass = new File(FILE_NAME_USERS_PASSWORD_NAMES);
        fileEmployeeList = new File(FILE_NAME_EMPLOYEE_LIST);
        try {
            if (!fileUsersNamePass.exists()) {
                fileUsersNamePass.createNewFile();
            }
            if (!fileEmployeeList.exists()) {
                fileEmployeeList.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Возвращает коллекцию сотрудников считанных из файла
     */
    public Set<Employee> readFromFileEmployeeList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.fileEmployeeList))) {
            return (Set<Employee>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return мапу хранящую логины и пароли сотрудников
     */
    public HashMap<String, String> readFromFileUserNamePassMap() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(this.fileUsersNamePass))) {
            return (HashMap<String, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * записывает в файл список сотрдуников
     *
     * @param employeeList список сотрудников для записи в файл
     */
    public void writeToFileEmployeeList(Set<Employee> employeeList) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.fileEmployeeList))) {
            oos.writeObject(employeeList);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * записывает в файл мапу логинов и паролей сотруднков
     *
     * @param usersNamePass входищая мапа для записи в файл
     */
    public void writeToFileUserNamePassMap(HashMap<String, String> usersNamePass) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(this.fileUsersNamePass))) {
            oos.writeObject(usersNamePass);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
