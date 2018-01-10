package models;

import database.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import validation.Pattern;
import validation.Validation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
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

    public static boolean validateBasicData(ArrayList<String> lista, ArrayList<String> telefony){
        boolean validateFlag = true;
        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(0))){
            //ustaw TextField Imię na czerwono
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(1))){
            //ustaw TextField Nazwisko na czerwono
            validateFlag = false;
        }
        if(!Validation.isInteger(lista.get(2))){
            //ustaw TextField Wiek na czerwono
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.emailPattern, lista.get(3))){
            //ustaw TextField E-mail na czerwono
            validateFlag = false;
        }
        if(!Validation.isDouble(lista.get(4))){
            //ustaw TextField Pensja na czerwono
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.postalCodePattern, lista.get(5))){
            //ustaw TextField kod pocztowy na czerwono
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(6))){
            //ustaw TextField miejscowosc na czerwono
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(7))){
            //ustaw TextField ulica na czerwono
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(8))){
            //ustaw TextField nrDomu na czerwono
            validateFlag = false;
        }

        for(int i = 0; i<telefony.size(); i++){
            if(!Validation.regexChecker(Pattern.phoneNumberPattern, telefony.get(i)) && !Validation.regexChecker(Pattern.phoneNumberHomePattern, telefony.get(i))) {
                //ustaw odpowiedni telefon na czerwono
                validateFlag = false;
            }
        }
        return validateFlag;
    }
}
