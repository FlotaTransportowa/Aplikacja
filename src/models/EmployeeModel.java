package models;

import database.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeModel {
    public static ObservableList<Employee> getAllEmployees(){
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        TypedQuery<Employee> query = entityManager.createQuery("select e from Employee e", Employee.class);
        List<Employee> employees1 = query.getResultList();
        entityManager.getTransaction().commit();

        employees.addAll(employees1);

        entityManager.close();
        entityManagerFactory.close();
        return employees;
    }
}
