package controllers;

import database.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import static database.Employee.findAllEmployees;
import static sample.Main.returnEmployee;


public class ShowEmployeeController extends Controller {

    @FXML private TableView<Employee> employeeTable;
    private  ObservableList<Employee> data;
    @FXML void initialize() throws Exception {

        data=FXCollections.observableArrayList(findAllEmployees());
        employeeTable.setItems(data);
        employeeTable.setEditable(true);
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

}
