package controllers;

import database.Driver;
import fxModels.TrackFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.table.TableRowExpanderColumn;
import validation.Validation;

/**
 * Kontroler do pokazywania Tras
 */
public class DriversTracksController extends Controller{
    @FXML private TextField searchField;
    @FXML private TableView<TrackFX> trackTable;
    private static ObservableList<TrackFX> data;
    private LoggedController loggedController;

    @FXML
    void initialize(){


        TableRowExpanderColumn<TrackFX> expander = new TableRowExpanderColumn<TrackFX>(this::createTrackExpander);
        expander.setMinWidth(30);
        expander.setMaxWidth(30);

        TableColumn<TrackFX, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<TrackFX, String> nameCol = new TableColumn<>("Nazwa");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        trackTable.getColumns().addAll(expander, idCol, nameCol);

    }

    /**
     * Uaktualnienie TableView wszystkimi trasami w bazie danych
     */
    public void initAll()
    {
        data = FXCollections.observableArrayList(TrackFX.getAll());
        trackTable.setItems(data);
        trackTable.setPlaceholder(new Label("Brak tras."));

        setSearchField();
    }

    /**
     * Uaktualnienie TableView trasami zalogowanego użytkownika
     */
    public void initYours()
    {
        data = FXCollections.observableArrayList(TrackFX.getAllNotExecutedDriverTracks((Driver) loggedController.getLoggedEmployee()));
        trackTable.setItems(data);

        setSearchField();
    }

    /**
     * Inicjalizacja obserwatora reagującego na wpisywane filtry
     */
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

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController = loggedController;
    }
}
