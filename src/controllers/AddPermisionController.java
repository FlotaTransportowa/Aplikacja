package controllers;

import database.Driver;
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

public class AddPermisionController extends Controller{
    @FXML private Label label;
    @FXML private VBox  vBox;
    @FXML private  HBox addNewHBox;

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
        permissionController.deleteForm();
        return node;
    }

    private HBox newPermissionForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/permissionForm.fxml"));
        HBox node = loader.load();
        PermissionController permissionController = loader.getController();
        permissionControllers.add(permissionController);
        permissionController.setAddPermisionController(this);
        permissionController.setDriver(driver);
        permissionController.newForm();

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

    public void createNewPermission() throws IOException {
        addNewHBox.getChildren().add(newPermissionForm());
    }
}
