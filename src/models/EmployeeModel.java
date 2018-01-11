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

    public static boolean validateBasicData(ArrayList<String> lista){
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
        if(!Validation.isDouble(lista.get(4)) || lista.get(4).isEmpty()){
            //ustaw TextField Pensja na czerwono
            System.out.println("Błąd z pensją");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.postalCodePattern, lista.get(5)) || lista.get(5).isEmpty()){
            //ustaw TextField kod pocztowy na czerwono
            System.out.println("Błąd z kodem");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(6)) || lista.get(6).isEmpty()){
            //ustaw TextField miejscowosc na czerwono
            System.out.println("Błąd z miejsowością");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(7)) || lista.get(7).isEmpty()){
            //ustaw TextField ulica na czerwono
            System.out.println("Błąd z ulicą");
            validateFlag = false;
        }
        if(lista.get(8).isEmpty()){
            //ustaw TextField nrDomu na czerwono
            System.out.println("Błąd z numerem domu");
            validateFlag = false;
        }
        return validateFlag;
    }

    public static boolean validatePhoneNumbers(ArrayList<String> telefony){
        boolean validateFlag = true;
        for(int i =0; i<telefony.size(); i++){
            if(!Validation.regexChecker(Pattern.phoneNumberPattern, telefony.get(i)) && !Validation.regexChecker(Pattern.phoneNumberHomePattern, telefony.get(i)) || telefony.get(i).isEmpty()){
                System.out.println("Źle: " + telefony.get(i));
                validateFlag = false;
            }
        }
        return validateFlag;
    }
}
