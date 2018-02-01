package models;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Model pracownika
 */
public class EmployeeModel implements BaseModel<Employee>{
    /**
     * @return Zwraca wszystkich pracowników
     */
    @Override
    public ObservableList<Employee> getAll() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        try {
            TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e", Employee.class);
            List<Employee> employees1 = query.getResultList();
            employees.addAll(employees1);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return employees;
    }

    /**
     * Dodaje kierowcy driver uprawnienie permission
     * @param driver kierowca
     * @param permission uprawnienie
     */
    public static void addPermission(Driver driver, Permission permission)
    {
        if(driver.getPermissions()==null){
            List<Permission> permissions = new ArrayList<Permission>();
            permissions.add(permission);
            driver.setPermissions(permissions);
        }
        if(!driver.getPermissions().contains(permission))        {
            driver.getPermissions().add(permission);
        }
    }

    /**
     * Sprawdza czy podane konto ma przypisanego pracownika
     * @param account Konto do sprawdzenia
     * @return Zwraca konto lub null gdy nie znaleziono
     */
    public static Driver getDriverByAccount(Account account){
        Driver driver = null;
        EntityManager entityManager = GlobalManager.getManager();

        try {
            entityManager.getTransaction().begin();
            TypedQuery<Driver> query = entityManager.createQuery("select e from Driver e where account = :account", Driver.class);
            query.setParameter("account", account);
            driver = query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return driver;
    }

    /**
     * Tworzy pracownika o podanych parametrach
     * @param name imie
     * @param lastName nazwisko
     * @param age wiek
     * @param gender płeć (Mężczyzna lub Kobieta)
     * @param type typ pracownika
     * @param email adres e-mail
     * @param salary Podstawa pensji
     * @return Zwraca jedną z klas dziedziczących po Employee
     */
    public static Employee getEmployee(String name, String lastName, int age, String gender, String type, String email, double salary){
        switch (type){
            case "Dyspozytor":
                return new Dispatcher(name, lastName, age, gender, type, email, salary);
            case "Kierownik":
                return new Principal(name, lastName, age, gender, type, email, salary);
            case "Kierowca":
                return new Driver(name, lastName, age, gender, type, email, salary);
        }
        return null;
    }

    /**
     * Szuka pracownika przypisanego do konta
     * @param account konto do sprawdzenia
     * @return Zwraca do pracownika lub null gdy nieznaleziono
     */
    public static Employee getEmployee(Account account){
        Employee employee = null;
        EntityManager entityManager = GlobalManager.getManager();

        try {
            entityManager.getTransaction().begin();
            TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where account = :account", Employee.class);
            query.setParameter("account", account);
            employee = query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return employee;
    }
}
