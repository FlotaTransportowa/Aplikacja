package controllers;




import database.Order;
import database.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.OrderModel;
import models.TrackModel;
import org.controlsfx.control.StatusBar;
import validation.Validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Kontroler do tworzenia tras
 */
public class AddNewTrackController extends Controller{

    @FXML private TextField searchField;
    @FXML private TextField trackName;
    @FXML private StatusBar statusBar;
    @FXML private TableView<Order> beforeAddTable;
    @FXML private TableView<Order> afterAddTable;
    private static ObservableList<Order> beforeData;
    private static ObservableSet<Order> afterData;
    private OrderModel orderModel = new OrderModel();
    private LoggedController loggedController;
    @FXML
    void initialize(){
        refreshView();
    }

    @FXML
    void addOrderToTrack(){
        if(beforeAddTable.getSelectionModel().getSelectedItem() == null)
            return;
        afterData.add(beforeAddTable.getSelectionModel().getSelectedItem());
        afterAddTable.setItems(FXCollections.observableArrayList(afterData));
    }

    @FXML
    void removeOrderFromTrack(){
        if(afterAddTable.getSelectionModel().getSelectedItem() != null){
            Order selectedItem = afterAddTable.getSelectionModel().getSelectedItem();
            afterData.remove(selectedItem);
            afterAddTable.getItems().remove(selectedItem);
        }
    }

    @FXML private void removeTab()
    {
        loggedController.removeTab(super.getThisTab());
    }

    @FXML
    void addTrack(){
        int counter = 0;
        Track track = new Track();
        List<Order> orders = new ArrayList<>();

        if(!valid()) {
            statusBar.setText("Wprowadź poprawną nazwę trasy...");
            return;
        }

        for(Order t : afterData) {
            t.setTrack(track);
            orders.add(t);
            counter++;
        }

        if(counter == 0) {
            statusBar.setText("Należy dodać przynajmniej jedno zlecenie do trasy...");
            return;
        }

        track.setName(trackName.getText());
        track.setExecuted(false);
        track.setOrders(orders);

        TrackModel trackModel = new TrackModel();
        trackModel.pushToDatabase(track);

        statusBar.setText("Pomyślnie utworzono trasę " + trackName.getText() + "!");

        refreshView();
    }

    /**
     * Odświeża widok
     */
    private void refreshView(){
        trackName.clear();

        for ( int i = 0; i<afterAddTable.getItems().size(); i++) {
            afterAddTable.getItems().clear();
        }

        afterData = FXCollections.observableSet();

        beforeData= FXCollections.observableArrayList(orderModel.getAllAlone());
        beforeAddTable.setItems(beforeData);

        setSearchField();
    }

    private boolean valid(){
        boolean validateFlag = true;
        if(!Validation.regexChecker(Pattern.streetPattern, trackName.getText()) || trackName.getText().isEmpty()){
            trackName.setStyle(nonValidStyle);
            validateFlag = false;
        }else trackName.setStyle(validStyle);

        return  validateFlag;
    }

    @FXML
    void setSearchField(){
        FilteredList<Order> filteredOrders = new FilteredList<Order>(beforeData, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredOrders.setPredicate(order -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if(Validation.isInteger(newValue) ){
                    if(order.getId() == Integer.parseInt(newValue.toLowerCase()))
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

        SortedList<Order> sortedData = new SortedList<>(filteredOrders);

        sortedData.comparatorProperty().bind(beforeAddTable.comparatorProperty());

        beforeAddTable.setItems(sortedData);
    }

    public LoggedController getLoggedController() {
        return loggedController;
    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController = loggedController;
    }
}
