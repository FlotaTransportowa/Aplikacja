package controllers;

import database.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import models.AccountModel;
import models.AddressModel;
import models.EmployeeModel;
import org.controlsfx.control.StatusBar;
import validation.Validation;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Kontroler widoku dodawania nowego pracownika
 */
public class AddEmployeeController extends Controller{
    private LoggedController loggedController;

    @FXML private StatusBar statusBar;

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
    private List<Phone> phones;

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
    private void addPhoneAction(){
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

    @FXML private void removeTab()
    {
        loggedController.removeTab(super.getThisTab());
    }

    /**
     * Akcja dodająca pracownika do bazy danych na podstawie wypełnionych pól formularza
     * @throws NoSuchAlgorithmException wyjątek
     */
    @FXML private void checkClick() throws NoSuchAlgorithmException {
        EmployeeModel employeeModel = new EmployeeModel();
        AddressModel addressModel = new AddressModel();
        ArrayList<Phone> phones;

        if(!valid())
            return;

        phones = getPhones();
        Address address = getAdress();

        String type = getType();
        String gender = getGender();
        newEmployeer(type,gender);

        Address addrTmp = AddressModel.retExist(address);
        if(addrTmp != null){
            employeer.setAddress(addrTmp);
        } else{
            employeer.setAddress(address);
        }
        employeer.setPhones(phones);

        AccountModel accountModel = new AccountModel();
        Account account = accountModel.generate(employeer.getLastName()+employeer.getFirstName());
        if(account != null) {
            employeer.setAccount(account);

            employeeModel.pushToDatabase(employeer);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Błąd dodawania pracownika");
            alert.setHeaderText("Pracownik istnieje w systemie");
            alert.showAndWait();
        }
        statusBar.setText("Dodawanie pracownika przebiegło pomyślnie.");
    }

    /**
     * Pobiera listę telefonów z pól tekstowych
     * @return Listę telefonów
     */
    private ArrayList<Phone> getPhones()
    {
        Phone numberOfPhone1 = null, numberOfPhone2 = null, numberOfPhone3 = null;
        ArrayList<Phone> phones = new ArrayList<>();
        if(!phoneHBox1.isDisable()) {
            numberOfPhone1 = new Phone();
            numberOfPhone1.setNumber(phone1.getText());
            numberOfPhone1.setType(phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString());
            phones.add(numberOfPhone1);
        }
        if(!phoneHBox2.isDisable()) {
            numberOfPhone2 = new Phone();
            numberOfPhone2.setNumber(phone2.getText());
            numberOfPhone2.setType(phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString());
            phones.add(numberOfPhone2);
        }
        if(!phoneHBox3.isDisable()) {
            numberOfPhone3 = new Phone();
            numberOfPhone3.setNumber(phone3.getText());
            numberOfPhone3.setType(phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString());
            phones.add(numberOfPhone3);
        }
        return phones;
    }

    /**
     * Tworzy pracwonika na podstawie uzupełnionego fomrularza
     * @param type typ
     * @param gender Płeć (Mężczyzna lub Kobieta)
     */
    private void newEmployeer(String type, String gender){
        employeer = EmployeeModel.getEmployee(addNameField.getText(), addSurnameField.getText(), Integer.parseInt(addAgeField.getText()), gender, type, addEmailField.getText(), Double.parseDouble(addSalaryField.getText()));
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
        this.phones = phones;
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

    /**
     * Dodaj do bazy danych
     */
    private void pushToDatabase()
    {
        System.out.println(employeer.getClass().getSimpleName());
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

    /**
     * Ustawienie pól formularza do przekazanego przez parametr pracownika
     * @param employee Pracwonik
     */
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
            if(!valid()) {
                statusBar.setText("Wszystkie pola muszą być poprawnie wypełnione.");
                return;
            }
            setEmployeer();
            employeer.setAddress(getAdress());
            List<Phone> phones1;
            phones1 = getPhones();
            if(phones1 == null) {
                statusBar.setText("Pracownik powinien posiadać przynajmniej jeden numer telefonu.");
                return;
            }

            employeer.setPhones(phones1);

            statusBar.setText("Edycja pracownika przebiegła pomyślnie.");

            pushToDatabase();
        });
    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController = loggedController;
    }

    /**
     * Walidacja poprawności wprowadzonych pól
     * @return Prawdę jeśli walidacja przeszła, fałsz w przeciwnym przypadku
     */
    @FXML
    private boolean valid(){
        boolean validateFlag = true, validAdressFlag, validPhoneNumbersFlag;
        if(!Validation.regexChecker(Pattern.stringPattern, addNameField.getText()) || addNameField.getText().isEmpty()){
            addNameField.setStyle(nonValidStyle);
            validateFlag = false;
        } else addNameField.setStyle(validStyle);
        if(!Validation.regexChecker(Pattern.stringPattern, addSurnameField.getText())  || addSurnameField.getText().isEmpty()){
            addSurnameField.setStyle(nonValidStyle);
            validateFlag = false;
        } else addSurnameField.setStyle(validStyle);
        if(!Validation.isInteger(addAgeField.getText()) || addAgeField.getText().isEmpty()){
            addAgeField.setStyle(nonValidStyle);
            validateFlag = false;
        } else addAgeField.setStyle(validStyle);
        if(!Validation.regexChecker(Pattern.emailPattern, addEmailField.getText())  || addEmailField.getText().isEmpty()){
            addEmailField.setStyle(nonValidStyle);
            validateFlag = false;
        } else addEmailField.setStyle(validStyle);
        if(!Validation.isDouble(addSalaryField.getText()) || addSalaryField.getText().isEmpty()) {
            addSalaryField.setStyle(nonValidStyle);
            validateFlag = false;
        } else addSalaryField.setStyle(validStyle);

        validAdressFlag = validAddressFields();
        validPhoneNumbersFlag = validAllPhoneNumbers();

        return validateFlag & validAdressFlag & validPhoneNumbersFlag;
    }
    /**
     * Walidacja poprawności pojedyńczego numeru telefonu
     * @return Prawdę jeśli walidacja przeszła, fałsz w przeciwnym przypadku
     */
    @FXML
    private boolean validPhoneNumber(TextField phone, String type) {
        boolean validateFlag = true;

        if(type.equals("Domowy")){
            if(!Validation.regexChecker(Pattern.phoneNumberHomePattern, phone.getText())) {
                phone.setStyle(nonValidStyle);
                validateFlag = false;
            } else phone.setStyle(validStyle);
        } else if(type.equals("Komórkowy")){
            if(!Validation.regexChecker(Pattern.phoneNumberPattern, phone.getText())){
                phone.setStyle(nonValidStyle);
                validateFlag = false;
            } else phone.setStyle(validStyle);
        }else {
            if(!Validation.regexChecker(Pattern.phoneNumberPattern, phone.getText()) && !Validation.regexChecker(Pattern.phoneNumberHomePattern, phone.getText())){
                phone.setStyle(nonValidStyle);
                validateFlag = false;
            } else phone.setStyle(validStyle);
        }

        return validateFlag;
    }
    /**
     * Walidacja poprawności wszystkich numerów telefonów
     * @return Prawdę jeśli walidacja przeszła, fałsz w przeciwnym przypadku
     */
    @FXML
    private boolean validAllPhoneNumbers(){
        boolean validateFlag = true;

        if(!phoneHBox1.isDisable()) {
            if(!validPhoneNumber(phone1, phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString())){
                phone1.setStyle(nonValidStyle);
                validateFlag = false;
            }else phone1.setStyle(validStyle);
        }
        if(!phoneHBox2.isDisable()) {
            if(!validPhoneNumber(phone2, phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString())){
                phone2.setStyle(nonValidStyle);
                validateFlag = false;
            }else phone2.setStyle(validStyle);
        }
        if(!phoneHBox3.isDisable()) {
            if(!validPhoneNumber(phone3, phoneTypeChoiseBox.getSelectionModel().getSelectedItem().toString())){
                phone3.setStyle(nonValidStyle);
                validateFlag = false;
            }else phone3.setStyle(validStyle);
        }

        return validateFlag;
    }

    /**
     * Walidacja poprawności adresu
     * @return Prawdę jeśli walidacja przeszła, fałsz w przeciwnym przypadku
     */
    @FXML
    private boolean validAddressFields(){
        boolean validateFlag = true;
        if(!Validation.regexChecker(Pattern.postalCodePattern, addPostalCodeField.getText()) || addPostalCodeField.getText().isEmpty()){
            addPostalCodeField.setStyle(nonValidStyle);
            validateFlag = false;
        } else addPostalCodeField.setStyle(validStyle);
        if(!Validation.regexChecker(Pattern.stringPattern, addLocalityField.getText()) || addLocalityField.getText().isEmpty()){
            addLocalityField.setStyle(nonValidStyle);
            validateFlag = false;
        }else addLocalityField.setStyle(validStyle);
        if(!Validation.regexChecker(Pattern.streetPattern, addStreetField.getText()) || addStreetField.getText().isEmpty()){
            addStreetField.setStyle(nonValidStyle);
            validateFlag = false;
        } else addStreetField.setStyle(validStyle);
        if(addHousenumField.getText().isEmpty()){
            addHousenumField.setStyle(nonValidStyle);
            validateFlag = false;
        }else addHousenumField.setStyle(validStyle);

        return validateFlag;
    }

}