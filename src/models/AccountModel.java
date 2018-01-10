package models;

import database.Account;
import database.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class AccountModel {
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
