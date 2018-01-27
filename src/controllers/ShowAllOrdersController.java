package controllers;

import database.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import models.OrderModel;
import validation.Validation;



public class ShowAllOrdersController extends Controller{

    @FXML private TextField searchField;
    @FXML private TableView<Order> orderTable;
    private static ObservableList<Order> dataOders;
    private OrderModel orderModel = new OrderModel();

    @FXML
    void initialize(){
        dataOders= FXCollections.observableArrayList(orderModel.getAll());
        orderTable.setItems(dataOders);
        setSearchField();
    }

    @FXML
    void setSearchField(){
        FilteredList<Order> filteredOrders = new FilteredList<Order>(dataOders, p -> true);

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

        sortedData.comparatorProperty().bind(orderTable.comparatorProperty());

        orderTable.setItems(sortedData);
    }

}
