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
    /**
     * Sprawdza czy istnieje kontro o podanym loginie i haśle
     * @param login
     * @param passw
     * @return Zwraca konto lub null jeśli nie znaleziono
     * @throws SQLException
     */
    public static Account getAccount(String login, String passw) throws SQLException {
        EntityManager entityManager = GlobalManager.getManager();
        Account account = null;
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Account> query = entityManager.createQuery("select a from Account a where a.login = :log and a.password = :pass", Account.class);
            query.setParameter("log", login);
            query.setParameter("pass", passw);
            account = query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
            return account;
        }
    }

    /**
     * @param account
     * @return Zwraca typ pracownika przypisanego do konta
     */
    public static String getEmployeeType(Account account){
        EntityManager entityManager = GlobalManager.getManager();
        long idAccount = account.getId();

        entityManager.getTransaction().begin();
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where accountID = :id", Employee.class);
        query.setParameter("id", idAccount);
        Employee employee = query.getSingleResult();
        entityManager.getTransaction().commit();

        return employee.getClass().getSimpleName();
    }

    /**
     * @param account
     * @return Zwraca wartośc paremtru typ pracownika
     */
    public static String getEmployeeTypePL(Account account){
        EntityManager entityManager = GlobalManager.getManager();
        long idAccount = account.getId();

        entityManager.getTransaction().begin();
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where accountID = :id", Employee.class);
        query.setParameter("id", idAccount);
        Employee employee = query.getSingleResult();
        entityManager.getTransaction().commit();

        return employee.getType();
    }

    /**
     * Tworzy konto o podanym loginie (login i hasło będą takie same)
     * @param login
     * @return Zwaraca konto lub null gdy login zajęty
     * @throws NoSuchAlgorithmException
     */
    public static Account generate(String login) throws NoSuchAlgorithmException {
        EntityManager entityManager = GlobalManager.getManager();
        Account accountExist;
        Account account = new Account();
        entityManager.getTransaction().begin();
        TypedQuery<Account> query = entityManager.createQuery("select a from Account a where a.login= :sur", Account.class);
        query.setParameter("sur", login);

        try{
            accountExist = query.getSingleResult();
            if(accountExist != null) {
                entityManager.getTransaction().commit();
                return null;
            }
        }catch(Exception e){

        }

        entityManager.getTransaction().commit();
        account.setLogin(login);
        account.setPassword(HashPassword.hashPassword(login));
        return account;
    }
}
