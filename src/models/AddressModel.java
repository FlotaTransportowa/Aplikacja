package models;

import database.Address;
import javafx.collections.ObservableList;
import validation.Pattern;
import validation.Validation;

import java.util.ArrayList;

public class AddressModel implements BaseModel<Address>{

    @Override
    public ObservableList<Address> getAll() {
        return null;
    }

    public boolean valid(String postal, String locality, String street, String houseNum) {
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
}
