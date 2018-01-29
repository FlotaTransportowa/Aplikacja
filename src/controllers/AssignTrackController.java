package controllers;

import database.*;
import fxModels.DriverWithPermsFX;
import fxModels.MachineFX;
import fxModels.TrackFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import org.controlsfx.control.table.TableRowExpanderColumn;
import validation.Validation;

import java.util.List;
import java.util.Optional;

public class AssignTrackController extends Controller{

    @FXML private TextField machineSearch, employeeSearch, trackSearch;
    @FXML private TableView<DriverWithPermsFX> driverWithPermsTable;
    @FXML private TableView<MachineFX> machineAssignTable;
    @FXML private TableView<TrackFX> trackAssignTable;
    @FXML private ObservableList<DriverWithPermsFX> driverData;
    @FXML private ObservableList<MachineFX> machineData;
    @FXML private ObservableList<TrackFX> trackData;

    @FXML
    private void initialize() {
        driverData = FXCollections.observableArrayList(DriverWithPermsFX.getAll());
        machineData = FXCollections.observableArrayList(MachineFX.getAll());
        trackData = FXCollections.observableArrayList(TrackFX.getAllNotAssigned());

        setTrackTable();
        setDriverTable();

        trackAssignTable.setItems(trackData);
        machineAssignTable.setItems(machineData);
        driverWithPermsTable.setItems(driverData);

        setSearchTrackField();
        setSearchMachineField();
        setSearchEmployeeField();
    }

    private GridPane createPermissionExpander(TableRowExpanderColumn.TableRowDataFeatures<DriverWithPermsFX> param) {

        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.setHgap(10);
        editor.setVgap(5);

        List<Permission> perms = param.getValue().getPerms();

        int rowCounter = 0;
        for (int i = 0; i < perms.size(); i++) {
            editor.addRow(rowCounter, new Label("Uprawnienie " + (i+1)));
            rowCounter++;
            editor.addRow(rowCounter, new Label("Nazwa: "), new Label(perms.get(i).getName()));
            rowCounter++;
            editor.addRow(rowCounter, new Label("Opis: "), new Label(perms.get(i).getDescription()));
            rowCounter++;
            editor.addRow(rowCounter, new Separator());
            rowCounter++;
        }

        if(rowCounter == 0)
            editor.addRow(2, new Label("Pracownik nie posiada żadnych uprawnień."), new Label(""));

        return editor;
    }

    @FXML
    void setDriverTable(){
        TableRowExpanderColumn<DriverWithPermsFX> expander = new TableRowExpanderColumn<DriverWithPermsFX>(this::createPermissionExpander);
        expander.setMinWidth(30);
        expander.setMaxWidth(30);

        TableColumn<DriverWithPermsFX, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

        TableColumn<DriverWithPermsFX, String> nameCol = new TableColumn<>("Imię");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<DriverWithPermsFX, String> lastNameCol = new TableColumn<>("Nazwisko");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<DriverWithPermsFX, String> typeCol = new TableColumn<>("Stanowisko");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<DriverWithPermsFX, Integer> ageCol = new TableColumn<>("Wiek");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));

        driverWithPermsTable.getColumns().addAll(expander, idCol, nameCol, lastNameCol, typeCol, ageCol);
    }

    @FXML
    void setTrackTable(){
        TableRowExpanderColumn<TrackFX> expander = new TableRowExpanderColumn<TrackFX>(this::createTrackExpander);
        expander.setMinWidth(30);
        expander.setMaxWidth(30);

        TableColumn<TrackFX, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TrackFX, String> nameCol = new TableColumn<>("Nazwa");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        trackAssignTable.getColumns().addAll(expander, idCol, nameCol);
    }

    @FXML
    void setSearchTrackField(){
        FilteredList<TrackFX> filteredOrders = new FilteredList<TrackFX>(trackData, p -> true);

        trackSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredOrders.setPredicate(track -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Validation.isInteger(newValue) ){
                    if(track.getId() == Integer.parseInt(newValue.toLowerCase()))
                        return true;
                } else {
                    if (track.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });

        SortedList<TrackFX> sortedData = new SortedList<>(filteredOrders);

        sortedData.comparatorProperty().bind(trackAssignTable.comparatorProperty());

        trackAssignTable.setItems(sortedData);
    }

    @FXML
    void setSearchMachineField(){
        FilteredList<MachineFX> filteredOrders = new FilteredList<MachineFX>(machineData, p -> true);

        machineSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredOrders.setPredicate(machineFX -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Validation.isInteger(newValue) ){
                    if(machineFX.getMachineID() == Integer.parseInt(newValue.toLowerCase()))
                        return true;
                } else {
                    if (machineFX.getMark().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (machineFX.getModel().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (machineFX.getType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (machineFX.getRegistrationNumber().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (machineFX.getVIN().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });

        SortedList<MachineFX> sortedData = new SortedList<>(filteredOrders);

        sortedData.comparatorProperty().bind(machineAssignTable.comparatorProperty());

        machineAssignTable.setItems(sortedData);
    }

    @FXML
    void setSearchEmployeeField(){
        FilteredList<DriverWithPermsFX> filteredOrders = new FilteredList<DriverWithPermsFX>(driverData, p -> true);

        employeeSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredOrders.setPredicate(driverFX -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Validation.isInteger(newValue) ){
                    if(driverFX.getEmployeeID() == Integer.parseInt(newValue.toLowerCase()))
                        return true;
                    else if(driverFX.getAge() == Integer.parseInt(newValue.toLowerCase()))
                        return true;
                } else {
                    if (driverFX.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (driverFX.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (driverFX.getType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });

        SortedList<DriverWithPermsFX> sortedData = new SortedList<>(filteredOrders);

        sortedData.comparatorProperty().bind(driverWithPermsTable.comparatorProperty());

        driverWithPermsTable.setItems(sortedData);
    }

    @FXML
    void assignTrack(){

    }
}
