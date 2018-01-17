package controllers;

import database.Employee;
import database.Permission;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.persistence.Embeddable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddPermisionController {
    @FXML private Label label;
    @FXML private VBox  vBox;

    private Employee employee;
    private List<PermissionController> permissionControllers = new ArrayList<PermissionController>();
    @FXML
    void initialize() throws IOException {
        vBox.getChildren().add(newPermissionForm());
        vBox.getChildren().add(newPermissionForm());

    }
    @FXML
    private void action()
    {
        List<Permission> permissions = new ArrayList<Permission>();
        for (PermissionController e:
                permissionControllers) {
            Permission p = e.getPermision();
            permissions.add(p);
            System.out.println(p.getDescription());
        }
    }
    private HBox newPermissionForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/permissionForm.fxml"));
        HBox node = loader.load();
        PermissionController permissionController = loader.getController();
        permissionControllers.add(permissionController);

        return node;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
