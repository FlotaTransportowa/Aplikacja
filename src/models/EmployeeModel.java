package models;

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

    public List<Object> consist(Driver employeer, String type, String gender, ArrayList<String> basic, ArrayList<String> addressInfo, ArrayList<String> phonesInfo, ArrayList<String> phoneTypes, ArrayList<Permission> perms) throws NoSuchAlgorithmException {
        List<Object> pracownik = new ArrayList<>();

        Phone phone1 = null, phone2 = null, phone3 = null;
        Address address = new Address();
        AccountModel accountModel = new AccountModel();
        ArrayList<Phone> phones = new ArrayList<>();

        employeer.setFirstName(basic.get(0));
        employeer.setLastName(basic.get(1));
        employeer.setAge(Integer.parseInt(basic.get(2)));
        employeer.setEmail(basic.get(3));
        employeer.setSalary(Double.parseDouble(basic.get(4)));
        address.setPostalCode(addressInfo.get(0));
        address.setLocality(addressInfo.get(1));
        address.setStreet(addressInfo.get(2));
        address.setApartmentNumber(addressInfo.get(3));

        employeer.setAddress(address);
        Account account = accountModel.generate(employeer.getLastName());
        employeer.setAccount(account);


        employeer.setGender(gender);
        employeer.setType(type);
        employeer.setPermissions(perms);
        for(int i=0; i<phonesInfo.size(); i++){
            if(i==0){
                phone1 = new Phone();
                phone1.setNumber(phonesInfo.get(0));
                phone1.setType(phoneTypes.get(0));
                phones.add(phone1);
            } else if(i == 1){
                phone2 = new Phone();
                phone2.setNumber(phonesInfo.get(1));
                phone2.setType(phoneTypes.get(1));
                phones.add(phone2);
            } else if(i == 2){
                phone3 = new Phone();
                phone3.setNumber(phonesInfo.get(2));
                phone3.setType(phoneTypes.get(2));
                phones.add(phone3);
            }
        }
        employeer.setPhones(phones);

        pracownik.add(employeer);
        pracownik.add(account);
        if(phone1 != null)
            pracownik.add(phone1);
        if(phone2 != null)
            pracownik.add(phone2);
        if(phone3 != null)
            pracownik.add(phone3);
        pracownik.add(address);
        pracownik.add(perms.get(0));

        return pracownik;
    }
}
