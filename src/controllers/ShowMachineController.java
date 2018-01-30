package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import fxModels.MachineFX;
import validation.Validation;


/**
 * Kontroler pokazujący listę maszyn
 */
public class ShowMachineController extends Controller{
    @FXML
    private TableView<MachineFX> machineShowTable;
    @FXML
    private ObservableList<MachineFX> data = FXCollections.observableArrayList();
    @FXML
    private TextField searchField;


    @FXML
    public void initialize() {
        data=FXCollections.observableArrayList(MachineFX.getAllAvailable());
        machineShowTable.setPlaceholder(new Label("Brak maszyn."));
        machineShowTable.setItems(data);
        setSearchField();
    }

    @FXML
    void setSearchField(){
        FilteredList<MachineFX> filteredOrders = new FilteredList<MachineFX>(data, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
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

        sortedData.comparatorProperty().bind(machineShowTable.comparatorProperty());

        machineShowTable.setItems(sortedData);
    }
}