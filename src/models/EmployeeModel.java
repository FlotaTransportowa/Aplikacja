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

    public boolean valid(String name, String surname, String age, String email, String salary) {
        boolean validateFlag = true;

        if(!Validation.regexChecker(Pattern.stringPattern, name) || name.isEmpty()){
            //ustaw TextField Imię na czerwono
            System.out.println("Błąd z imieniem");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, surname)  || surname.isEmpty()){
            //ustaw TextField Nazwisko na czerwono
            System.out.println("Błąd z nazwiskiem");
            validateFlag = false;
        }
        if(!Validation.isInteger(age) || age.isEmpty()){
            //ustaw TextField Wiek na czerwono
            System.out.println("Błąd z wiekiem");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.emailPattern, email)  || email.isEmpty()){
            //ustaw TextField E-mail na czerwono
            System.out.println("Błąd z emailem");
            validateFlag = false;
        }
        if(!Validation.isDouble(salary) || salary.isEmpty()) {
            //ustaw TextField Pensja na czerwono
            System.out.println("Błąd z pensją");
            validateFlag = false;
        }
        return validateFlag;
    }
}
