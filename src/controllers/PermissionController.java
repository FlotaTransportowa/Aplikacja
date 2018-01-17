package controllers;

import database.Permission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import models.PermissionModel;

public class PermissionController {
    private static PermissionModel permissionModel = new PermissionModel();
    private ObservableList<Permission> permissions;

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
}