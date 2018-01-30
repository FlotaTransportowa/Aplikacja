package controllers;

import database.Driver;
import database.Track;
import fxModels.OrderFX;
import database.Order;
import fxModels.TrackFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import models.EmployeeModel;
import models.TrackModel;
import org.controlsfx.control.StatusBar;
import org.controlsfx.control.table.TableRowExpanderColumn;
import validation.Validation;

import javax.persistence.TypedQuery;
import java.util.List;

public class ShowAllTracksAndTakeController extends Controller{
    @FXML
    private TextField searchField;
    @FXML private TableView<TrackFX> trackTable;
    @FXML private TableView<OrderFX> orderTable;
    @FXML private Pane orderPane;
    @FXML private Pane trackPane;
    @FXML private StatusBar statusBar;
    private static ObservableList<TrackFX> data;
    private static ObservableList<OrderFX> dataOrders;
    private static TrackFX trackFX;

    @FXML
    void initialize(){
        orderPane.setVisible(false);
        data = FXCollections.observableArrayList(TrackFX.getAllNotExecutedDriverTracks(EmployeeModel.getDriverByAccount(account)));

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

    void setOrderTable(){

        TableRowExpanderColumn<OrderFX> expander = new TableRowExpanderColumn<OrderFX>(this::createOrderExpander);
        expander.setMinWidth(30);
        expander.setMaxWidth(30);

        TableColumn<OrderFX, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<OrderFX, String> titleCol = new TableColumn<>("Tytuł");
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<OrderFX, String> typeCol = new TableColumn<>("Typ");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<OrderFX, String> deadlineCol = new TableColumn<>("Deadline");
        deadlineCol.setCellValueFactory(new PropertyValueFactory<>("timeLimitForCompletion"));

        TableColumn<OrderFX, String> stateCol = new TableColumn<>("Stan zlecenia");
        stateCol.setCellValueFactory(new PropertyValueFactory<>("orderState"));

        orderTable.getColumns().addAll(expander, idCol, titleCol, typeCol, deadlineCol, stateCol);

        refreshView();
        setSearchField();
    }

    @FXML
    void takeSelectedTrack(){
        trackFX = getSelectedTrack();
        if(!checkSelect(trackFX)) return;

        trackPane.setVisible(false);
        orderPane.setVisible(true);

        List<Order> orders = trackFX.getOrders();

        for(Order o : orders)
            o.getState().takeOrder(o, entityManager);

        statusBar.setText("Podjęto trasę");

        setOrderTable();
    }

    @FXML
    public Track findTrack(TrackFX trackFX){
        Track track = null;
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Track> query = entityManager.createQuery("select o from Track o where id = :identifier", Track.class);
            query.setParameter("identifier", trackFX.getId());
            track = query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }
        return track;
    }

    @FXML
    TrackFX getSelectedTrack(){
        return trackTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    boolean checkSelect(TrackFX trackFX){
        boolean flag = true;

        if(trackFX == null) {
            flag = false;
        }

        return flag;
    }

    @FXML
    void doneTheOrder(){
        OrderFX orderFX = getSelectedOrder();

        if(!checkSelect(orderFX)) return;

        Order order = findOrder(orderFX);

        order.getState().finishOrder(order, entityManager, statusBar);

        refreshView();
    }

    void refreshView(){
        dataOrders = FXCollections.observableArrayList(OrderFX.getTrackOrdersWithoutCanceledAndDone(trackFX));
        if(dataOrders.isEmpty()) {
            Track track = findTrack(trackFX);
            TrackModel.setExecuted(track);
            trackPane.setVisible(true);
            orderPane.setVisible(false);
            data = FXCollections.observableArrayList(TrackFX.getAllNotExecutedDriverTracks(EmployeeModel.getDriverByAccount(account)));
            trackTable.setItems(data);
            return;
        }
        orderTable.setItems(dataOrders);
    }

    @FXML
    boolean checkSelect(OrderFX orderFX){
        boolean flag = true;

        if(orderFX == null) {
            flag = false;
        }

        return flag;
    }

    @FXML
    OrderFX getSelectedOrder(){
        return orderTable.getSelectionModel().getSelectedItem();
    }
}
