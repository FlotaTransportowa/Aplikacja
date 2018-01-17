package controllers;

import database.Driver;
import database.Employee;
import database.Permission;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.EmployeeModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPermisionController {
    @FXML private Label label;
    @FXML private VBox  vBox;

    private Driver driver;
    private List<PermissionController> permissionControllers = new ArrayList<PermissionController>();
    private List<Permission> permissions;
    @FXML
    void initialize() throws IOException {


//       vBox.getChildren().add(newPermissionForm());
//       vBox.getChildren().add(newPermissionForm());
    }

    public void createCurrentPermissions() throws IOException {
        vBox.getChildren().clear();
        for (Permission p:
             permissions) {
                vBox.getChildren().add(currentPermissionForm(p));
        }
    }

    @FXML
    private void action()
    {
        List<Permission> permissions = new ArrayList<Permission>();
        for (PermissionController e:
                permissionControllers) {
            Permission p = e.getPermision();
            EmployeeModel.addPermission((Driver) driver,p);
            permissions.add(p);
            System.out.println(p.getDescription());
        }
    }
    private HBox currentPermissionForm(Permission permission) throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/permissionForm.fxml"));
        HBox node = loader.load();
        PermissionController permissionController = loader.getController();
        permissionControllers.add(permissionController);

        permissionController.setDriver(driver);
        permissionController.setPermission(permission);
        permissionController.setAddPermisionController(this);
        permissionController.newForm();
        return node;
    }

    private HBox newPermissionForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/permissionForm.fxml"));
        HBox node = loader.load();
        PermissionController permissionController = loader.getController();
        permissionControllers.add(permissionController);

        return node;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
        getFromDBPermission();
    }

    private void getFromDBPermission()
    {
        permissions=driver.getPermissions();
    }
}
