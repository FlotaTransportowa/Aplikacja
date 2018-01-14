package models;

import database.Phone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;
import validation.Pattern;
import validation.Validation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class PhoneModel implements BaseModel<Phone>{
    @Override
    public ObservableList<Phone> getAll() {
        ObservableList<Phone> phones = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        TypedQuery<Phone> query = entityManager.createQuery("select e from Phone e", Phone.class);
        List<Phone> phones1 = query.getResultList();
        entityManager.getTransaction().commit();

        phones.addAll(phones1);
        return phones;
    }

    @Override
    public boolean valid(ArrayList<String> lista) {
        boolean validateFlag = true;
        for(int i =0; i<lista.size(); i++){
            if(!Validation.regexChecker(Pattern.phoneNumberPattern, lista.get(i)) && !Validation.regexChecker(Pattern.phoneNumberHomePattern, lista.get(i))){
                System.out.println("Zły telefon z formularza " + (i+1) + " : " + lista.get(i));
                validateFlag = false;
            }
        }
        return validateFlag;
    }
}
