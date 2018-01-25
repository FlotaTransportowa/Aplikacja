package controllers;

import database.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.OrderModel;

public class AddNewTrackController extends Controller{

    @FXML private TableView<Order> beforeAddTable;
    @FXML private TableView<Order> afterAddTable;
    private static ObservableList<Order> beforeData;
    private static ObservableList<Order> afterData;
    private OrderModel orderModel = new OrderModel();

    @FXML
    void initialize(){
        beforeData= FXCollections.observableArrayList(orderModel.getAll());
        beforeAddTable.setItems(beforeData);
    }

    @FXML
    void addOrderToTrack(){

    }

    @FXML
    void addTrack(){

    }

    private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col ;
        return null ;
    }

}
