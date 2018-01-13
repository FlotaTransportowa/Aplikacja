package models;

import database.Account;
import database.Employee;
import sample.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.sql.SQLException;

public class AccountModel {
    public static Account getAccount(String login, String passw) throws SQLException {
        Account account = null;
        try {

            GlobalManager.getManager().getTransaction().begin();
            TypedQuery<Account> query = GlobalManager.getManager().createQuery("select a from Account a where a.login = :log and a.password = :pass", Account.class);
            query.setParameter("log", login);
            query.setParameter("pass", passw);
            account = query.getSingleResult();
            GlobalManager.getManager().getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }
    public static String getEmployeeType(Account account){
        long idEmployee = account.getId();

        GlobalManager.getManager().getTransaction().begin();
        TypedQuery<Employee> query = GlobalManager.getManager().createQuery("select e from Employee e where e.id = :id", Employee.class);
        query.setParameter("id", idEmployee);
        Employee employee = query.getSingleResult();
        GlobalManager.getManager().getTransaction().commit();

        return employee.getClass().getSimpleName();
    }

    public static String getEmployeeTypePL(Account account){
        long idEmployee = account.getId();

        GlobalManager.getManager().getTransaction().begin();
        TypedQuery<Employee> query = GlobalManager.getManager().createQuery("select e from Employee e where e.id = :id", Employee.class);
        query.setParameter("id", idEmployee);
        Employee employee = query.getSingleResult();
        GlobalManager.getManager().getTransaction().commit();

        return employee.getType();
    }
}
