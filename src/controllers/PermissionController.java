package controllers;

import database.Driver;
import database.Permission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
        button.setText("Dodaj");
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

    public void newForm() {
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