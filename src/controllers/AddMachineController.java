package controllers;

import database.Machine;
import database.MachineType;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.MachineModel;
import validation.Pattern;
import validation.Validation;

/**
 * Kontroler do dodawania maszyn
 */
public class AddMachineController extends Controller{

    @FXML private TextField addModel;
    @FXML private TextField addMark;
    @FXML private TextField addType;
    @FXML private TextField addRegistrationNum;
    @FXML private TextField addVIN;

    private LoggedController loggedController;
    @FXML
    void initialize(){
    }

    @FXML private void removeTab()
    {
        loggedController.removeTab(super.getThisTab());
    }
    @FXML
    private void addMachine(){
        if(!valid())
            return;
        MachineModel machineModel = new MachineModel();
        MachineType existType = null, newType = new MachineType(), type = null;
        newType.setModel(addModel.getText());
        newType.setType(addType.getText());
        newType.setMark(addMark.getText());

        existType = machineModel.retExistType(newType);

        if(existType != null){
            type = existType;
        } else{
            type = newType;
        }

        Machine machine = machineModel.getMachine(type, addRegistrationNum.getText(), addVIN.getText());
        machineModel.pushToDatabase(machine);
    }

    /**
     * Walidacja poprawności wprowadzonych pól
     * @return Prawdę jeśli walidacja przeszła, fałsz w przeciwnym przypadku
     */
    @FXML
    private boolean valid(){
        boolean validateFlag = true;

        if(!Validation.regexChecker(Pattern.streetPattern, addModel.getText()) || addModel.getText().isEmpty()){
            addModel.setStyle(nonValidStyle);
            validateFlag = false;
        } else addModel.setStyle(validStyle);
        if(!Validation.regexChecker(Pattern.stringPattern, addMark.getText())  || addMark.getText().isEmpty()){
            addMark.setStyle(nonValidStyle);
            validateFlag = false;
        } else addMark.setStyle(validStyle);
        if(!Validation.regexChecker(Pattern.stringPattern, addType.getText())  || addType.getText().isEmpty()){
            addType.setStyle(nonValidStyle);
            validateFlag = false;
        } else addType.setStyle(validStyle);
        if(!Validation.regexChecker(Pattern.registrationNumberPattern, addRegistrationNum.getText())  || addRegistrationNum.getText().isEmpty()){
            addRegistrationNum.setStyle(nonValidStyle);
            validateFlag = false;
        } else addRegistrationNum.setStyle(validStyle);
        if(!Validation.regexChecker(Pattern.VINPattern, addVIN.getText())  || addVIN.getText().isEmpty()){
            addVIN.setStyle(nonValidStyle);
            validateFlag = false;
        } else addVIN.setStyle(validStyle);

        return validateFlag;
    }

    public LoggedController getLoggedController() {
        return loggedController;
    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController = loggedController;
    }
}
