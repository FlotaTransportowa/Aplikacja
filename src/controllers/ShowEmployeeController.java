package controllers;

import database.Employee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import manager.GlobalManager;
import models.EmployeeModel;
import validation.Validation;

import javax.persistence.EntityManager;
import java.io.IOException;


public class ShowEmployeeController extends Controller {

    private LoggedController loggedController;

    @FXML private TextField searchField;
    @FXML private TableView<Employee> employeeTable;
    private static ObservableList<Employee> data;
    private EmployeeModel employeeModel = new EmployeeModel();

    @FXML void initialize() throws Exception {
        data=FXCollections.observableArrayList(employeeModel.getAll());
        employeeTable.setItems(data);

        FilteredList<Employee> filteredOrders = new FilteredList<Employee>(data, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredOrders.setPredicate(employeer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Validation.isInteger(newValue) ){
                    if(employeer.getId() == Integer.parseInt(newValue.toLowerCase()))
                        return true;
                } else {
                    if (employeer.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employeer.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (employeer.getType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });

        SortedList<Employee> sortedData = new SortedList<>(filteredOrders);

        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());

        employeeTable.setItems(sortedData);

        for (Employee e:data
             ) {
            ButtonBar buttonBar = e.getButtonBar();
            if(buttonBar.getButtons().size()>0)
                continue;
            Button editButton = new Button("Edytuj");
            editButton.setOnAction(ev->{
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
            Button deleteButton = new Button("Usuń");
            deleteButton.setOnAction(ev->{
                        EntityManager entityManager = GlobalManager.getManager();
                        entityManager.getTransaction().begin();
                        try {
                            entityManager.remove(e);
                        }catch (Exception ex){
                            ex.printStackTrace();
                        } finally {
                            entityManager.getTransaction().commit();
                        }
                        System.out.println("Usunięto "+e.getId());
                        try {
                            initialize();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
            );
            Button permissionButton = new Button("Uprawnienia");
            permissionButton.setOnAction(ev->{
                        try {
                            loggedController.addEmployeePermission(e);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
            );

            ButtonBar.setButtonData(editButton, ButtonBar.ButtonData.BIG_GAP);
            ButtonBar.setButtonData(deleteButton, ButtonBar.ButtonData.BIG_GAP);
            ButtonBar.setButtonData(permissionButton, ButtonBar.ButtonData.BIG_GAP);
            buttonBar.getButtons().addAll(editButton,deleteButton,permissionButton);
        }
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
