package database;

import javax.persistence.*;
import java.sql.SQLException;

@Entity
public class Account {
    @Id
    @GeneratedValue
    private long id;
    private String login;
    private String password;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public static Account getAccount(String login, String passw) throws SQLException {
        Account account = null;
        try {
            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
            EntityManager entityManager = entityManagerFactory.createEntityManager();

            entityManager.getTransaction().begin();
            TypedQuery<Account> query = entityManager.createQuery("select a from Account a where a.login = :log and a.password = :pass", Account.class);
            query.setParameter("log", login);
            query.setParameter("pass", passw);
            account = query.getSingleResult();
            entityManager.getTransaction().commit();

            entityManager.close();
            entityManagerFactory.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    public static String getEmployeeType(Account account){
        long idEmployee = account.getId();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where e.id = :id", Employee.class);
        query.setParameter("id", idEmployee);
        Employee employee = query.getSingleResult();
        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();

        return employee.getClass().getSimpleName();
    }
}
