package models;

import database.Account;
import database.Employee;
import manager.GlobalManager;
import security.HashPassword;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class AccountModel {
    public static Account getAccount(String login, String passw) throws SQLException {
        EntityManager entityManager = GlobalManager.getManager();
        Account account = null;
        try {

            entityManager.getTransaction().begin();
            TypedQuery<Account> query = entityManager.createQuery("select a from Account a where a.login = :log and a.password = :pass", Account.class);
            query.setParameter("log", login);
            query.setParameter("pass", passw);
            account = query.getSingleResult();
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }
    public static String getEmployeeType(Account account){
        EntityManager entityManager = GlobalManager.getManager();
        long idEmployee = account.getId();

        entityManager.getTransaction().begin();
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where e.id = :id", Employee.class);
        query.setParameter("id", idEmployee);
        Employee employee = query.getSingleResult();
        entityManager.getTransaction().commit();

        return employee.getClass().getSimpleName();
    }

    public static String getEmployeeTypePL(Account account){
        EntityManager entityManager = GlobalManager.getManager();
        long idEmployee = account.getId();

        entityManager.getTransaction().begin();
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where e.id = :id", Employee.class);
        query.setParameter("id", idEmployee);
        Employee employee = query.getSingleResult();
        entityManager.getTransaction().commit();

        return employee.getType();
    }

    public static Account generate(String surname) throws NoSuchAlgorithmException {
        Account account = new Account();
        account.setLogin(surname);
        account.setPassword(HashPassword.hashPassword(surname));
        return account;
    }
}
