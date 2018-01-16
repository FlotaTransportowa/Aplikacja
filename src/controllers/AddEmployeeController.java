package controllers;

import database.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.AccountModel;
import models.AddressModel;
import models.EmployeeModel;
import models.PhoneModel;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class AddEmployeeController extends Controller{
    private LoggedController loggedController;
    //private Employee employee;

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
    @FXML private Employee employeer = null;

    @FXML private ToggleGroup group = new ToggleGroup();

    @FXML private HBox phoneHBox1;
    @FXML private HBox phoneHBox2;
    @FXML private HBox phoneHBox3;

    @FXML private Button actionButton;

    @FXML private AnchorPane anchorPane;

    @FXML
    void initialize(){
        typeOfEmployeeChoiceBox.setItems(FXCollections.observableArrayList(
                "Kierowca", "Dyspozytor", "Kierownik")
        );
        phoneTypeChoiseBox.setItems(FXCollections.observableArrayList(
                "domowy", "służbowy", "komórkowy")
        );
        phoneTypeChoiseBox1 .setItems(FXCollections.observableArrayList(
                "domowy", "służbowy", "komórkowy")
        );
        phoneTypeChoiseBox2.setItems(FXCollections.observableArrayList(
                "domowy", "służbowy", "komórkowy")
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
        //Phone numberOfPhone1 = null, numberOfPhone2 = null, numberOfPhone3 = null;
        ArrayList<Phone> phones ;//= new ArrayList<>();

        if(employeeModel.valid(addNameField.getText(), addSurnameField.getText(), addAgeField.getText(), addEmailField.getText(), addSalaryField.getText()) && addressModel.valid(addPostalCodeField.getText(), addLocalityField.getText(), addStreetField.getText(), addHousenumField.getText())) {
            System.out.println("Correct!");

            phones = getPhones(phoneModel);
            Address address = getAdress();

            String type = getType();
            String gender = getGender();
            newEmployeer(type,gender);


            employeer.setAddress(address);
            employeer.setPhones(phones);

            //tu dodajemy gołego pracownika bez uprawnień, by potem móc je nadać w osobnej karcie

            AccountModel accountModel = new AccountModel();
            Account account = accountModel.generate(employeer.getLastName()+employeer.getFirstName());
            if(account != null)
            employeer.setAccount(account);

            employeeModel.pushToDatabase(employeer);

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addPermisionScreen.fxml"));
            anchorPane.getChildren().clear();
            try {
                anchorPane.getChildren().add(loader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private ArrayList<Phone> getPhones(PhoneModel phoneModel)
    {
        Phone numberOfPhone1 = null, numberOfPhone2 = null, numberOfPhone3 = null;
        ArrayList<Phone> phones = new ArrayList<>();
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
                numberOfPhone2.setNumber(phone2.getText());
                numberOfPhone2.setType(phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString());
                phones.add(numberOfPhone2);
            }
        }
        if(!phoneHBox3.isDisable()) {
            if(phoneModel.valid(phone3.getText(), phoneTypeChoiseBox2.getSelectionModel().getSelectedItem().toString())){
                numberOfPhone3 = new Phone();
                numberOfPhone3.setNumber(phone3.getText());
                numberOfPhone3.setType(phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString());
                phones.add(numberOfPhone3);
            }

        }
        return phones;
    }

    private void newEmployeer(String type, String gender){
        switch (type){
            case "Dyspozytor":
                employeer = new Dispatcher(addNameField.getText(), addSurnameField.getText(), Integer.parseInt(addAgeField.getText()), gender, type, addEmailField.getText(), Double.parseDouble(addSalaryField.getText()));
                break;
            case "Kierownik":
                employeer = new Principal(addNameField.getText(), addSurnameField.getText(), Integer.parseInt(addAgeField.getText()), gender, type, addEmailField.getText(), Double.parseDouble(addSalaryField.getText()));
                break;
            case "Kierowca":
                employeer = new Driver(addNameField.getText(), addSurnameField.getText(), Integer.parseInt(addAgeField.getText()), gender, type, addEmailField.getText(), Double.parseDouble(addSalaryField.getText()));
                break;
            default:
                employeer = null;
        }
    }
    private void setEmployeer()
    {
        employeer.setFirstName(addNameField.getText());
        employeer.setLastName(addSurnameField.getText());
        employeer.setAge(Integer.valueOf(addAgeField.getText()));
        employeer.setGender(getGender());
        employeer.setType(getType());
        employeer.setEmail(addEmailField.getText());
        employeer.setSalary(Double.parseDouble(addSalaryField.getText()));
    }
    private String getType()
    {
        return typeOfEmployeeChoiceBox.getSelectionModel().getSelectedItem().toString();
    }

    private String getGender()
    {
        return group.getSelectedToggle().getUserData().toString();
    }
    private Address getAdress()
    {
        return new Address(addLocalityField.getText(), addPostalCodeField.getText(), addStreetField.getText(), addHousenumField.getText());
    }
    private void setEmployee(Employee employee)
    {
        addNameField.setText(employee.getFirstName());
        addSurnameField.setText(employee.getLastName());
        addAgeField.setText(String.valueOf(employee.getAge()));
        addEmailField.setText(employee.getEmail());
        if (employee.getGender().equals("Mężczyzna")) {
            man.setSelected(true);
        } else {
            woman.setSelected(true);
        }
        addSalaryField.setText(String.valueOf(employee.getSalary()));
    }
    private void setAdress(Address address)
    {
        addPostalCodeField.setText(address.getPostalCode());
        addLocalityField.setText(address.getLocality());
        addStreetField.setText(address.getStreet());
        addHousenumField.setText(address.getApartmentNumber());
    }
    private void setPhones(List<Phone> phones)
    {
        if(phones.size()>0){
            phoneHBox1.setDisable(false);
            phoneHBox1.setVisible(true);
            phone1.setText(phones.get(0).getNumber());
            phoneTypeChoiseBox.getSelectionModel().select(phones.get(0).getType());
        }
        if(phones.size()>1){
            phoneHBox2.setDisable(false);
            phoneHBox2.setVisible(true);
            phone2.setText(phones.get(1).getNumber());
            phoneTypeChoiseBox1.getSelectionModel().select(phones.get(1).getType());
        }
        if(phones.size()>2){
            phoneHBox3.setDisable(false);
            phoneHBox3.setVisible(true);
            phone3.setText(phones.get(2).getNumber());
            phoneTypeChoiseBox2.getSelectionModel().select(phones.get(2).getType());
        }
    }
    private void setType(String type)
    {
        typeOfEmployeeChoiceBox.getSelectionModel().select(type);
    }

    private void pushToDatabase()
    {
        EmployeeModel employeeModel = new EmployeeModel();
        switch (getType()){
            case "Kierowca":
                employeeModel.pushToDatabase((Driver) employeer);
                break;
            case "Kierownik":
                employeeModel.pushToDatabase((Principal) employeer);
                break;
            case "Dyspozytor":
                employeeModel.pushToDatabase((Dispatcher) employeer);
                break;
        }

    }
    public void setToEmployee(Employee employee)
    {
        this.employeer = (Driver) employee;
        setEmployee(employee);

        Address address = employee.getAddress();
        setAdress(address);

        List<Phone> phones = employee.getPhones();
        setPhones(phones);

        setType(employee.getType());

        actionButton.setText("Zapisz");
        actionButton.setOnAction(e->{
            setEmployeer();
            employeer.setAddress(getAdress());
            PhoneModel phoneModel = new PhoneModel();
            employeer.setPhones(getPhones(phoneModel));

            pushToDatabase();
        });
    }


}
