package entites;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class AccountingSystem {

    public static final AccountingSystem INSTANCE = new AccountingSystem();

    private HashMap<String, String> userNamePass;
    private Set<Employee> employees;

    private AccountingSystem() {
        this.userNamePass = new HashMap<>();
        this.employees = new HashSet<>();
    }

    public void loadData(HashMap<String, String> userNamePass, Set<Employee> employees) {
        this.userNamePass = userNamePass;
        this.employees = employees;
    }

    public HashMap<String, String> getUserNamePass() {
        return userNamePass;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

}
