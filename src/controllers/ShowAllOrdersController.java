package controllers;

import database.Address;
import database.Order;
import database.Track;
import fxModels.OrderFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import models.OrderModel;
import org.controlsfx.control.table.TableRowExpanderColumn;
import validation.Validation;

import java.util.List;


public class ShowAllOrdersController extends Controller {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<OrderFX> orderTable;
    private static ObservableList<OrderFX> dataOders;

    @FXML
    void initialize() {
        dataOders = FXCollections.observableArrayList(OrderFX.getAll());

        TableRowExpanderColumn<OrderFX> expander = new TableRowExpanderColumn<OrderFX>(this::createExpander);
        expander.setMinWidth(30);
        expander.setMaxWidth(30);

        TableColumn<OrderFX, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<OrderFX, String> titleCol = new TableColumn<>("Tytu");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<OrderFX, String> typeCol = new TableColumn<>("Typ");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<OrderFX, String> deadlineCol = new TableColumn<>("Deadline");
        deadlineCol.setCellValueFactory(new PropertyValueFactory<>("timeLimitForCompletion"));

        TableColumn<OrderFX, String> stateCol = new TableColumn<>("Stan zlecenia");
        stateCol.setCellValueFactory(new PropertyValueFactory<>("orderState"));

        orderTable.getColumns().addAll(expander, idCol, titleCol, typeCol, deadlineCol, stateCol);

        orderTable.setItems(dataOders);

        setSearchField();
    }

    @FXML
    void setSearchField() {
        FilteredList<OrderFX> filteredOrders = new FilteredList<OrderFX>(dataOders, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredOrders.setPredicate(order -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (Validation.isInteger(newValue)) {
                    if (order.getId() == Integer.parseInt(newValue.toLowerCase()))
                        return true;
                } else {
                    if (order.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (order.getType().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                }
                return false;
            });
        });

        SortedList<OrderFX> sortedData = new SortedList<>(filteredOrders);

        sortedData.comparatorProperty().bind(orderTable.comparatorProperty());

        orderTable.setItems(sortedData);
    }

    private GridPane createExpander(TableRowExpanderColumn.TableRowDataFeatures<OrderFX> param) {

        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.setHgap(10);
        editor.setVgap(5);

        int rowIndex = 0;
        editor.addRow(rowIndex, new Label("Adres"));
        editor.addRow(++rowIndex, new Label("Kod pocztowy: "), new Label(param.getValue().getPostalCode()));
        editor.addRow(++rowIndex, new Label("Miejscowość: "), new Label(param.getValue().getLocality()));
        editor.addRow(++rowIndex, new Label("Ulica: "), new Label(param.getValue().getStreet()));
        editor.addRow(++rowIndex, new Label("Numer domu: "), new Label(param.getValue().getApartmentNumer()));
        editor.addRow(++rowIndex, new Separator());

        if (param.getValue().getComment() == null) {
            editor.addRow(++rowIndex, new Label("Komentarz"));
            editor.addRow(++rowIndex, new Label(param.getValue().getComment()));
        } else {
            editor.addRow(++rowIndex, new Label(""));
        }

        editor.addRow(++rowIndex, new Separator());

        Track track = param.getValue().getTrack();

        if(track == null)
            editor.addRow(++rowIndex, new Label("Zlecenie nie należy do żadnej trasy."));
        else
            editor.addRow(++rowIndex, new Label("Zlecenie należy do trasy o identyfikatorze: " + track.getId()));

        return editor;
    }

}
