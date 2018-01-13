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

    @Override
    public boolean valid(ArrayList<String> lista) {
        boolean validateFlag = true;
        if(!Validation.regexChecker(Pattern.postalCodePattern, lista.get(0)) || lista.get(0).isEmpty()){
            //ustaw TextField kod pocztowy na czerwono
            System.out.println("Błąd z kodem");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(1)) || lista.get(1).isEmpty()){
            //ustaw TextField miejscowosc na czerwono
            System.out.println("Błąd z miejsowością");
            validateFlag = false;
        }
        if(!Validation.regexChecker(Pattern.stringPattern, lista.get(2)) || lista.get(2).isEmpty()){
            //ustaw TextField ulica na czerwono
            System.out.println("Błąd z ulicą");
            validateFlag = false;
        }
        if(lista.get(3).isEmpty()){
            //ustaw TextField nrDomu na czerwono
            System.out.println("Błąd z numerem domu");
            validateFlag = false;
        }
        return validateFlag;
    }
}
