package models;

import database.Address;
import database.Employee;
import javafx.collections.ObservableList;
import manager.GlobalManager;
import validation.Pattern;
import validation.Validation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

public class AddressModel implements BaseModel<Address>{

    @Override
    public ObservableList<Address> getAll() {
        return null;
    }

    public static boolean valid(String postal, String locality, String street, String houseNum) {
        boolean validateFlag = true;
        if(!Validation.regexChecker(Pattern.postalCodePattern, postal) || postal.isEmpty()){
            //ustaw TextField kod pocztowy na czerwono
            System.out.println("Błąd z kodem");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, locality) || locality.isEmpty()){
            //ustaw TextField miejscowosc na czerwono
            System.out.println("Błąd z miejsowością");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, street) || street.isEmpty()){
            //ustaw TextField ulica na czerwono
            System.out.println("Błąd z ulicą");
            validateFlag = false;
        }
        if(houseNum.isEmpty()){
            //ustaw TextField nrDomu na czerwono
            System.out.println("Błąd z numerem domu");
            validateFlag = false;
        }
        return validateFlag;
    }

    public static Address retExist(Address address){
        Address existAddress = null;
        EntityManager entityManager = GlobalManager.getManager();

        try {
            entityManager.getTransaction().begin();
            TypedQuery<Address> query = entityManager.createQuery("select a from Address a where postalCode = :code and locality = :local and street = :street and apartmentNumber = :houseNum", Address.class);
            query.setParameter("code", address.getPostalCode());
            query.setParameter("local", address.getLocality());
            query.setParameter("street", address.getStreet());
            query.setParameter("houseNum", address.getApartmentNumber());
            existAddress = query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return existAddress;
    }
}
