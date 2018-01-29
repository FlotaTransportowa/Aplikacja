package controllers;

import database.*;
import fxModels.TrackFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import models.TrackModel;
import org.controlsfx.control.table.TableRowExpanderColumn;
import validation.Validation;

import java.util.List;
import java.util.Optional;

public class ShowAllTracksController extends Controller{

    @FXML private TextField searchField;
    @FXML private TableView<TrackFX> trackTable;
    private static ObservableList<TrackFX> data;

    @FXML
    void initialize(){
        data = FXCollections.observableArrayList(TrackFX.getAll());

        TableRowExpanderColumn<TrackFX> expander = new TableRowExpanderColumn<TrackFX>(this::createTrackExpander);
        expander.setMinWidth(30);
        expander.setMaxWidth(30);

        TableColumn<TrackFX, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TrackFX, String> nameCol = new TableColumn<>("Nazwa");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        trackTable.getColumns().addAll(expander, idCol, nameCol);

        trackTable.setItems(data);

        setSearchField();
    }

    @FXML
    void setSearchField(){
        FilteredList<TrackFX> filteredOrders = new FilteredList<TrackFX>(data, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
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

        sortedData.comparatorProperty().bind(trackTable.comparatorProperty());

        trackTable.setItems(sortedData);
    }
}
