package models;

import controllers.Controller;
import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e", Employee.class);
        List<Employee> employees1 = query.getResultList();
        entityManager.getTransaction().commit();

        employees.addAll(employees1);
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
}
