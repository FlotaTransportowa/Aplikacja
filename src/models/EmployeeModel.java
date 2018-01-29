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

    public static Employee getEmployee(Account account)
    {
        EntityManager entityManager = GlobalManager.getManager();
        Employee employee = null;
        entityManager.getTransaction().begin();
        try {
            TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e where accountId = :accId", Employee.class);
            query.setParameter("accId",account.getId());
            employee = query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return employee;
    }
}
