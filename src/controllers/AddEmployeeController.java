package controllers;

import database.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.AddressModel;
import models.EmployeeModel;
import models.PhoneModel;

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
        ArrayList<String> lista = new ArrayList<>();
        ArrayList<String> lista2 = new ArrayList<>();
        lista.add(addNameField.getText());
        lista.add(addSurnameField.getText());
        lista.add(addAgeField.getText());
        lista.add(addEmailField.getText());
        lista.add(addSalaryField.getText());
        lista2.add(addPostalCodeField.getText());
        lista2.add(addLocalityField.getText());
        lista2.add(addStreetField.getText());
        lista2.add(addHousenumField.getText());
        ArrayList<String> tel = new ArrayList<>();
        ArrayList<String> telType = new ArrayList<>();
        if(!phoneHBox1.isDisable()) {
            tel.add(phone1.getText());
            telType.add(phoneTypeChoiseBox.getItems().get(0).toString());
        }
        if(!phoneHBox2.isDisable()) {
            tel.add(phone2.getText());
            telType.add(phoneTypeChoiseBox.getItems().get(1).toString());
        }
        if(!phoneHBox3.isDisable()) {
            tel.add(phone3.getText());
            telType.add(phoneTypeChoiseBox.getItems().get(2).toString());
        }
        if(employeeModel.valid(lista) && phoneModel.valid(tel) && addressModel.valid(lista2)) {
            System.out.println("Correct!");
            String type = typeOfEmployeeChoiceBox.getSelectionModel().getSelectedItem().toString();
            String gender = group.getSelectedToggle().getUserData().toString();

            if(type.equals("Dyspozytor")){
                employeer = new Dispatcher();
            } else if(type.equals("Kierownik")){
                employeer = new Principal();
            }else
                employeer = new Driver();

            //dodaję na sztywno bo nie ma jeszcze odpowiedniego widoczku(nad nim musimy się poważniej zastanowić)
            Permission permission1 = new Permission();
            permission1.setName("Prawo jazdy kat. B");
            permission1.setDescription("Uprawnienie do prowadzenia...");
            ArrayList<Permission> listOfPerms = new ArrayList<>();
            listOfPerms.add(permission1);

            create(employeeModel.consist(employeer, type, gender, lista, lista2, tel, telType, listOfPerms));
        }
    }
}
