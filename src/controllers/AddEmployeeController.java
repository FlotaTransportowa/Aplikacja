package controllers;

import database.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.AccountModel;
import models.AddressModel;
import models.EmployeeModel;
import models.PhoneModel;

import java.nio.file.AccessDeniedException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeeController extends Controller{
    @FXML
    private ChoiceBox phoneTypeChoiseBox,phoneTypeChoiseBox1,phoneTypeChoiseBox2, typeOfEmployeeChoiceBox;
    @FXML private TextField addNameField;
    @FXML private TextField addSurnameField;
    @FXML private TextField addAgeField;
    @FXML private TextField addEmailField;
    @FXML private TextField addSalaryField;
    @FXML private TextField addPostalCodeField;
    @FXML private TextField addLocalityField;
    @FXML private TextField addStreetField;
    @FXML private TextField addHousenumField;
    @FXML private TextField phone1;
    @FXML private TextField phone2;
    @FXML private TextField phone3;
    @FXML private RadioButton woman;
    @FXML private RadioButton man;
    @FXML private Driver employeer = null;

    @FXML private ToggleGroup group = new ToggleGroup();

    @FXML private HBox phoneHBox1;
    @FXML private HBox phoneHBox2;
    @FXML private HBox phoneHBox3;

    @FXML
    void initialize(){
        typeOfEmployeeChoiceBox.setItems(FXCollections.observableArrayList(
                "Kierowca", "Dyspozytor", "Kierownik")
        );
        phoneTypeChoiseBox.setItems(FXCollections.observableArrayList(
                "Domowy", "Służbowy", "Komórkowy")
        );
        phoneTypeChoiseBox1 .setItems(FXCollections.observableArrayList(
                "Domowy", "Służbowy", "Komórkowy")
        );
        phoneTypeChoiseBox2.setItems(FXCollections.observableArrayList(
                "Domowy", "Służbowy", "Komórkowy")
        );
        phoneTypeChoiseBox.getSelectionModel().selectFirst();
        phoneTypeChoiseBox1.getSelectionModel().selectFirst();
        phoneTypeChoiseBox2.getSelectionModel().selectFirst();
        typeOfEmployeeChoiceBox.getSelectionModel().selectFirst();
        woman.setUserData("Kobieta");
        woman.setToggleGroup(group);
        man.setUserData("Mężczyzna");
        man.setToggleGroup(group);
    }

    @FXML
    private void addAction(){
        if(!phoneHBox2.isVisible()){
            phoneHBox2.setVisible(true);
            phoneHBox2.setDisable(false);
        }
        else if(!phoneHBox3.isVisible()){
            phoneHBox3.setVisible(true);
            phoneHBox3.setDisable(false);
        }
    }

    @FXML private  void remove2ndPhoneBox(){
        phoneHBox2.setVisible(false);
        phoneHBox2.setDisable(true);
    }

    @FXML private  void remove3rdPhoneBox(){
        phoneHBox3.setVisible(false);
        phoneHBox3.setDisable(true);
    }

    @FXML private void checkClick() throws NoSuchAlgorithmException {
        EmployeeModel employeeModel = new EmployeeModel();
        PhoneModel phoneModel = new PhoneModel();
        AddressModel addressModel = new AddressModel();
        Phone numberOfPhone1 = null, numberOfPhone2 = null, numberOfPhone3 = null;
        ArrayList<Phone> phones = new ArrayList<>();

        if(employeeModel.valid(addNameField.getText(), addSurnameField.getText(), addAgeField.getText(), addEmailField.getText(), addSalaryField.getText()) && addressModel.valid(addPostalCodeField.getText(), addLocalityField.getText(), addStreetField.getText(), addHousenumField.getText())) {
            System.out.println("Correct!");

            if(!phoneHBox1.isDisable()) {
                if(phoneModel.valid(phone1.getText(), phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString())){
                    numberOfPhone1 = new Phone();
                    numberOfPhone1.setNumber(phone1.getText());
                    numberOfPhone1.setType(phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString());
                    phones.add(numberOfPhone1);
                }
            }
            if(!phoneHBox2.isDisable()) {
                if(phoneModel.valid(phone2.getText(), phoneTypeChoiseBox1.getSelectionModel().getSelectedItem().toString())){
                    numberOfPhone2 = new Phone();
                    numberOfPhone2.setNumber(phone1.getText());
                    numberOfPhone2.setType(phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString());
                    phones.add(numberOfPhone2);
                }
            }
            if(!phoneHBox3.isDisable()) {
                if(phoneModel.valid(phone3.getText(), phoneTypeChoiseBox2.getSelectionModel().getSelectedItem().toString())){
                    numberOfPhone3 = new Phone();
                    numberOfPhone3.setNumber(phone1.getText());
                    numberOfPhone3.setType(phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString());
                    phones.add(numberOfPhone3);
                }
            }

            String type = typeOfEmployeeChoiceBox.getSelectionModel().getSelectedItem().toString();
            String gender = group.getSelectedToggle().getUserData().toString();

            if(type.equals("Dyspozytor")){
                employeer = new Dispatcher(addNameField.getText(), addSurnameField.getText(), Integer.parseInt(addAgeField.getText()), gender, type, addEmailField.getText(), Double.parseDouble(addSalaryField.getText()));
            } else if(type.equals("Kierownik")){
                employeer = new Principal(addNameField.getText(), addSurnameField.getText(), Integer.parseInt(addAgeField.getText()), gender, type, addEmailField.getText(), Double.parseDouble(addSalaryField.getText()));
            }else
                employeer = new Driver(addNameField.getText(), addSurnameField.getText(), Integer.parseInt(addAgeField.getText()), gender, type, addEmailField.getText(), Double.parseDouble(addSalaryField.getText()));

            Address address = new Address(addLocalityField.getText(), addPostalCodeField.getText(), addStreetField.getText(), addHousenumField.getText());
            employeer.setAddress(address);
            employeer.setPhones(phones);

            Permission permission1 = new Permission();
            permission1.setName("Prawo jazdy kat. B");
            permission1.setDescription("Uprawnienie do prowadzenia...");
            ArrayList<Permission> listOfPerms = new ArrayList<>();
            listOfPerms.add(permission1);

            employeer.setPermissions(listOfPerms);

            //dodaję na sztywno bo nie ma jeszcze odpowiedniego widoczku(nad nim musimy się poważniej zastanowić)

            AccountModel accountModel = new AccountModel();
            Account account = accountModel.generate(employeer.getLastName());
            employeer.setAccount(account);

            employeeModel.pushToDatabase(employeer);
        }
    }
}
