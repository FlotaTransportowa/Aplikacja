package controllers;




import database.Order;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import models.OrderModel;
import org.controlsfx.control.PropertySheet;

public class AddNewTrackController extends Controller{

    @FXML private TableView<Order> beforeAddTable;
    @FXML private TableView<Order> afterAddTable;
    private static ObservableList<Order> beforeData;
    private static ObservableSet<Order> afterData = FXCollections.observableSet();
    private OrderModel orderModel = new OrderModel();

    @FXML
    void initialize(){
        beforeData= FXCollections.observableArrayList(orderModel.getAll());
        beforeAddTable.setItems(beforeData);
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

    @FXML
    void addTrack(){

    }



    private <T> TableColumn<T, ?> getTableColumnByName(TableView<T> tableView, String name) {
        for (TableColumn<T, ?> col : tableView.getColumns())
            if (col.getText().equals(name)) return col ;
        return null ;
    }

}
