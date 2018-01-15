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

    public boolean valid(String phoneNum, String type) {
        boolean validateFlag = true;
        if(type.equals("Domowy")){
            if(!Validation.regexChecker(Pattern.phoneNumberHomePattern, phoneNum))
                validateFlag = false;
        } else if(type.equals("Komórkowy")){
            if(!Validation.regexChecker(Pattern.phoneNumberPattern, phoneNum)){
                validateFlag = false;
            }
        }else {
            if(!Validation.regexChecker(Pattern.phoneNumberPattern, phoneNum) && !Validation.regexChecker(Pattern.phoneNumberHomePattern, phoneNum)){
                validateFlag = false;
            }
        }
        return validateFlag;
    }
}
