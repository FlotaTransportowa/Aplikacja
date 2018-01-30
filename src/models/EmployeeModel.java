package models;

import controllers.Controller;
import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import manager.GlobalManager;
import security.HashPassword;
import validation.Pattern;
import validation.Validation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

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
     * @param driver
     * @param permission
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
     * @param account
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
     * @param name
     * @param lastName
     * @param age
     * @param gender
     * @param type
     * @param email
     * @param salary
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
     * @param account
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
