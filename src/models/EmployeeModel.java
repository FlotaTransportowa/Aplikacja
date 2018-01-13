package models;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.GlobalManager;
import validation.Pattern;
import validation.Validation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
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

    @Override
    public boolean valid(ArrayList<String> lista) {
        boolean validateFlag = true;

        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(0)) || lista.get(0).isEmpty()){
            //ustaw TextField Imię na czerwono
            System.out.println("Błąd z imieniem");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(1))  || lista.get(1).isEmpty()){
            //ustaw TextField Nazwisko na czerwono
            System.out.println("Błąd z nazwiskiem");
            validateFlag = false;
        }
        if(!Validation.isInteger(lista.get(2))  || lista.get(2).isEmpty()){
            //ustaw TextField Wiek na czerwono
            System.out.println("Błąd z wiekiem");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.emailPattern, lista.get(3))  || lista.get(3).isEmpty()){
            //ustaw TextField E-mail na czerwono
            System.out.println("Błąd z emailem");
            validateFlag = false;
        }
        if(!Validation.isDouble(lista.get(4)) || lista.get(4).isEmpty()) {
            //ustaw TextField Pensja na czerwono
            System.out.println("Błąd z pensją");
            validateFlag = false;
        }
        return validateFlag;
    }
}
