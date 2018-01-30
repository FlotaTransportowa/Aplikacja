package controllers;

import database.Address;
import database.Driver;
import database.Employee;
import database.Phone;
import fxModels.EmployeeFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import org.controlsfx.control.StatusBar;
import validation.Validation;

import javax.persistence.TypedQuery;
import java.io.IOException;
import java.util.List;

/**
 * Kontroler pokazujący listę pracowników
 */
public class ShowAllEmployeesController extends Controller{

    private LoggedController loggedController;

    @FXML
    private StatusBar statusBar;
    @FXML
    private TextField searchField;
    @FXML private TableView<EmployeeFX> employeeTable;
    private static ObservableList<EmployeeFX> dataEmployees;


    @FXML
    void initialize(){
        employeeTable.setPlaceholder(new Label("Lista pracowników jest pusta."));
        refreshView();
        employeeTable.setRowFactory( tv -> {
            TableRow<EmployeeFX> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    EmployeeFX rowData = row.getItem();
                    showEmployeeDetails(rowData);
                }
            });
            return row ;
        });
    }

    private void setSearchField(){
        FilteredList<EmployeeFX> filteredEmployees = new FilteredList<EmployeeFX>(dataEmployees, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredEmployees.setPredicate(employeer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Validation.isInteger(newValue) ){
                    if(employeer.getId() == Integer.parseInt(newValue.toLowerCase()))
                        return true;
                    else if(employeer.getAge() == Integer.parseInt(newValue.toLowerCase()))
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

        SortedList<EmployeeFX> sortedData = new SortedList<>(filteredEmployees);

        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());

        employeeTable.setItems(sortedData);
    }

    private void refreshView(){
        dataEmployees = FXCollections.observableArrayList(EmployeeFX.getAll());
        employeeTable.setItems(dataEmployees);
        setSearchField();
    }

    private void showEmployeeDetails(EmployeeFX employeeFX){
        Alert dlg = new Alert(Alert.AlertType.INFORMATION);
        dlg.setGraphic(null);

        int rowIndex = 0, columnIndex = 0;
        Address address = employeeFX.getAddressOfEmployee();
        List<Phone> phones = employeeFX.getPhones();

        GridPane grid = new GridPane();
        grid.setPrefWidth(450);
        grid.setHgap(30);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        dlg.setTitle("Dane pracownika");
        dlg.setHeaderText("Szczegóły dotyczące pracownika: " + employeeFX.getFirstName() + " " + employeeFX.getLastName());

        grid.add(new Label("Płeć: " + employeeFX.getGender()), columnIndex, ++rowIndex);
        grid.add(new Label("Email: " + employeeFX.getEmail()), columnIndex, ++rowIndex);
        grid.add(new Label("Pensja: " + employeeFX.getSalary()), columnIndex, ++rowIndex);
        grid.add(new Label("Adres"), columnIndex, ++rowIndex);
        grid.add(new Label("Miejscowość: " + address.getLocality()), columnIndex, ++rowIndex);
        grid.add(new Label("Kod pocztowy: " + address.getPostalCode()), columnIndex, ++rowIndex);
        grid.add(new Label("Ulica: " + address.getStreet()), columnIndex, ++rowIndex);
        grid.add(new Label("Numer domu: " + address.getStreet()), columnIndex, ++rowIndex);
        grid.add(new Label("Numery telefonów"), ++columnIndex, rowIndex=1);
        for(Phone p : phones)
            grid.add(new Label(p.getType() +" "+ p.getNumber()), columnIndex, ++rowIndex);

        dlg.getDialogPane().setContent(grid);

        dlg.showAndWait();
    }

    @FXML
    void actionEditEmployee() throws IOException {
        EmployeeFX employeeFX = getEmployeeFX();
        if(!checkSelection(employeeFX)) {
            statusBar.setText("Należy wybrać pracownika do edycji.");
            return;
        }

        Driver driver = findEmployee(employeeFX);

        loggedController.editEmployee(driver);
    }

    private Driver findEmployee(EmployeeFX employeeFx){
        Driver driver = null;

        try {
            entityManager.getTransaction().begin();
            TypedQuery<Driver> query = entityManager.createQuery("select o from Driver o where id = :identifier", Driver.class);
            query.setParameter("identifier", employeeFx.getId());
            driver = query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return driver;
    }

    private EmployeeFX getEmployeeFX(){
        return employeeTable.getSelectionModel().getSelectedItem();
    }

    private boolean checkSelection(EmployeeFX employeeFX){
        if(employeeFX == null)
            return false;
        return true;
    }

    @FXML
    void actionManagePermissions() throws IOException {
        EmployeeFX employeeFX = getEmployeeFX();
        if(!checkSelection(employeeFX)) {
            statusBar.setText("Należy wybrać pracownika w celu ustawienia uprawnień.");
            return;
        }

        Driver driver = findEmployee(employeeFX);

        loggedController.addEmployeePermission(driver);
    }

    @FXML
    void actionRemoveEmployee(){
        EmployeeFX employeeFX = getEmployeeFX();
        if(!checkSelection(employeeFX)) {
            statusBar.setText("Należy wybrać pracownika do usunięcia.");
            return;
        }

        Driver driver = findEmployee(employeeFX);

        removeEmployeer(driver);

        statusBar.setText("Usunięto pracownika o ID: " + driver.getId());

        refreshView();
    }

    private void removeEmployeer(Employee employee){
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(employee);
        }catch (Exception ex){
            ex.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController = loggedController;
    }
}
