package controllers;

import database.Driver;
import database.Machine;
import database.Track;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import models.TrackModel;
import validation.Validation;

import java.util.Optional;

public class ShowAllTracksController extends Controller{

    @FXML private TextField searchField;
    @FXML private TableView<Track> trackTable;
    private static ObservableList<Track> data;
    private TrackModel trackModel = new TrackModel();

    @FXML
    void initialize(){
        data = FXCollections.observableArrayList(trackModel.getAll());
        trackTable.setItems(data);
        setSearchField();

        trackTable.setRowFactory( tv -> {
            TableRow<Track> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) ) {
                    showTrackDetails(row.getItem());
                }
            });
            return row ;
        });
    }

    @FXML
    void showTrackDetails(Track track){
        // Create the custom dialog.
        Dialog dialog = new Dialog<>();
        dialog.setTitle("Szczegóły trasy");

        ButtonType closeButtonType = new ButtonType("Zamknij", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(closeButtonType);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        Machine machine = track.getMachine();
        Driver driver = track.getDriver();

        if(driver == null || machine == null){
            grid.add(new Label("Trasa nie jest jeszcze przydzielona."), 0, 0);
        } else {
            grid.add(new Label("ID kierowcy: " + driver.getId()), 0, 0);
            grid.add(new Label("Imię kierowcy: " + driver.getFirstName()), 0, 1);
            grid.add(new Label("Nazwisko kierowcy: " + driver.getLastName()), 0, 2);
            grid.add(new Label("ID maszyny: " + machine.getId()), 0, 3);
            grid.add(new Label("Marka maszyny: " + machine.getType().getMark()), 0, 4);
            grid.add(new Label("Model maszyny: " + machine.getType().getModel()), 0, 5);
        }

        dialog.getDialogPane().setContent(grid);

        Optional<Pair<String, String>> result = dialog.showAndWait();
    }

    @FXML
    void setSearchField(){
        FilteredList<Track> filteredOrders = new FilteredList<Track>(data, p -> true);

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

        SortedList<Track> sortedData = new SortedList<>(filteredOrders);

        sortedData.comparatorProperty().bind(trackTable.comparatorProperty());

        trackTable.setItems(sortedData);
    }
}
