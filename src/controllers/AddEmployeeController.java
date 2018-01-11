package controllers;

import database.Driver;
import database.Employee;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.EmployeeModel;
import validation.Validation;

import java.util.ArrayList;

public class AddEmployeeController {
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
    @FXML private RadioButton woman;
    @FXML private RadioButton man;

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

    @FXML private void checkClick(){
        ArrayList<String> lista = new ArrayList<>();
        lista.add(addNameField.getText());
        lista.add(addSurnameField.getText());
        lista.add(addAgeField.getText());
        lista.add(addEmailField.getText());
        lista.add(addSalaryField.getText());
        lista.add(addPostalCodeField.getText());
        lista.add(addLocalityField.getText());
        lista.add(addStreetField.getText());
        lista.add(addHousenumField.getText());
        System.out.println(typeOfEmployeeChoiceBox.getSelectionModel().getSelectedItem().toString()); //Wybrany typ pracownika
        System.out.println(group.getSelectedToggle().getUserData().toString());
        if(EmployeeModel.validateBasicData(lista)){
            System.out.println("Jest dobrze");
        }
    }
}
