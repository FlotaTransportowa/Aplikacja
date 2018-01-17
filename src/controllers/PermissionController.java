package controllers;

import database.Driver;
import database.Permission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import models.DriverModel;
import models.PermissionModel;

import java.io.IOException;

public class PermissionController {
    private static PermissionModel permissionModel = new PermissionModel();
    private ObservableList<Permission> permissions;
    private AddPermisionController addPermisionController;

    private Driver driver;
    private Permission permission;
    @FXML private ChoiceBox choiceBox;
    @FXML private Button button;
    @FXML
    void initialize(){

        permissions = permissionModel.getAll();
        choiceBox.setItems(FXCollections.observableArrayList(permissions));

    }

    public Permission getPermision()
    {
        Permission cbSelection = (Permission) choiceBox.getSelectionModel().getSelectedItem();
        return cbSelection;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public AddPermisionController getAddPermisionController() {
        return addPermisionController;
    }

    public void setAddPermisionController(AddPermisionController addPermisionController) {
        this.addPermisionController = addPermisionController;
    }
    public void newForm()
    {
        button.setText("Dodaj");
        button.setOnAction(e->{
            Permission perm = (Permission) choiceBox.getSelectionModel().getSelectedItem();
            if(driver==null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Błąd - niepoprawny kierowca");
                alert.setHeaderText("Kierowca jest nieprawidłowy");
                alert.showAndWait();
            }
            else if(perm==null){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Błąd - Nie wybrano uprawnienia");
                alert.setHeaderText("Wybierz uprawnienie do dodania");
                alert.showAndWait();
            }
            else {
                DriverModel.addDriverPermission(driver, perm);
                try {
                    addPermisionController.createCurrentPermissions();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
    public void deleteForm() {
        choiceBox.setDisable(true);
        choiceBox.getSelectionModel().select(permission);
        button.setText("Usuń");
        button.setOnAction(e->{
            DriverModel.deleteDriverPermission(driver,permission);
            try {
                addPermisionController.createCurrentPermissions();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

    }
}