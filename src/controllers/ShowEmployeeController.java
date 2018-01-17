package controllers;

import database.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import manager.GlobalManager;
import models.EmployeeModel;

import java.io.IOException;
import java.util.List;


public class ShowEmployeeController extends Controller {

    private LoggedController loggedController;
    private List<AddPermission> addEmployeeControllers;

    @FXML private TableView<Employee> employeeTable;
    private TableColumn employeeEdit;
    private  ObservableList<Employee> data;
    private EmployeeModel employeeModel = new EmployeeModel();
    @FXML void initialize() throws Exception {
        data=FXCollections.observableArrayList(employeeModel.getAll());
        employeeTable.setItems(data);

        for (Employee e:data
             ) {
            e.getEditButton().setOnAction(ev->{
                        if(e==null)
                            System.out.println(e.getId());
                        else
                        try {
                            loggedController.editEmployee(e);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
            );
            e.getDeleteButton().setOnAction(ev->{
                        System.out.println("Usunięto "+e.getId());
                        GlobalManager.getManager().remove(e);
                        try {
                            initialize();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
            );
            e.getPermissionButton().setOnAction(ev->{
                        try {
                            loggedController.addEmployeePermission(e);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
            );
        }
        employeeEdit = getTableColumnByName(employeeTable,"col4");
    }

    @FXML
    void saveData(){

        data = employeeTable.getItems();
        for (Employee e: data
             ) {
            System.out.println(e.getId()+" "+e.getLastName());
        }
        System.out.println("Zapisano");
    }

    private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col ;
        return null ;
    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController = loggedController;
    }
}
