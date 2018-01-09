package controllers;

import database.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.EmployeeModel;



import static sample.Main.returnEmployee;


public class ShowEmployeeController extends Controller {

    @FXML private TableView<Employee> employeeTable;
    private TableColumn employeeEdit;
    private  ObservableList<Employee> data;
    @FXML void initialize() throws Exception {
        data=FXCollections.observableArrayList(EmployeeModel.getAllEmployees());
        employeeTable.setItems(data);

        for (Employee e:data
             ) {
            e.getEditButton().setOnAction(ev->{System.out.println(e.getId());}
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
}
