package controllers;

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
import org.controlsfx.control.StatusBar;
import org.controlsfx.control.table.TableRowExpanderColumn;
import validation.Validation;


public class ShowAllOrdersController extends Controller {

    @FXML
    private TextField searchField;
    @FXML
    private TableView<OrderFX> orderTable;
    private static ObservableList<OrderFX> dataOders;
    @FXML private StatusBar statusBar;
    OrderFX orderForEdit;

    private LoggedController loggedController;

    @FXML
    void initialize() {
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
    }

    public void initAll()
    {
        dataOders = FXCollections.observableArrayList(OrderFX.getAll());

        orderTable.setItems(dataOders);
        setSearchField();
    }
    public void initYours() {
        dataOders = FXCollections.observableArrayList(OrderFX.getEmployeeOrders(loggedController.getLoggedEmployee()));

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
                    } else if (order.getOrderState().toLowerCase().contains(lowerCaseFilter)) {
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

    @FXML
    void setCancelOrder(){
        OrderFX toCancelOrder = getSelectedValue();

        if(!checkSelect(toCancelOrder)) return;

        Order order = findOrder(toCancelOrder);

        order.getState().cancelOrder(order, entityManager, statusBar);

        refreshView();
    }

    @FXML
    void setPauseOrder(){
        OrderFX toPauselOrder = getSelectedValue();

        if(!checkSelect(toPauselOrder)) return;

        Order order = findOrder(toPauselOrder);

        order.getState().pauseOrder(order, entityManager, statusBar);

        refreshView();
    }

    @FXML
    void setUnpauseOrder(){
        OrderFX toUnpauselOrder = getSelectedValue();

        if(!checkSelect(toUnpauselOrder)) return;

        Order order = findOrder(toUnpauselOrder);

        order.getState().unpauseOrder(order, entityManager, statusBar);

        refreshView();
    }


    @FXML
    void setUnconfirmOrder(){
        OrderFX toUnonfirmOrder = getSelectedValue();

        if(!checkSelect(toUnonfirmOrder)) return;

        Order order = findOrder(toUnonfirmOrder);

        order.getState().unconfirmOrder(order, entityManager, statusBar);

        refreshView();
    }
    @FXML
    void setConfirmOrder(){
        OrderFX toConfirmOrder = getSelectedValue();

        if(!checkSelect(toConfirmOrder)) return;

        if(checkFields(toConfirmOrder)) {
            statusBar.setText("Zlecenie nie ma wypełnionego pola komentarza...");
            return;
        }

        Order order = findOrder(toConfirmOrder);

        order.getState().confirmOrder(order, entityManager, statusBar);

        refreshView();
    }

    //ToDo Daniel jakbyś mógł tu obsłużyć tworzenie okna do Zaksięgowania zlecenia
    @FXML
    void setPostTheOrder(){

    }

    @FXML
    OrderFX getSelectedValue(){
        return orderTable.getSelectionModel().getSelectedItem();
    }

    @FXML
    boolean checkSelect(OrderFX orderFX){
        boolean flag = true;

        if(orderFX == null) {
            statusBar.setText("Należy zaznaczyć zlecenie w tabeli...");
            flag = false;
        }

        return flag;
    }

    @FXML
    boolean checkFields(OrderFX order){
        return order.getComment().isEmpty();
    }

    @FXML
    private void refreshView(){
        dataOders = FXCollections.observableArrayList(OrderFX.getAll());
        orderTable.setItems(dataOders);
        orderTable.refresh();
        setSearchField();
    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController = loggedController;
    }
}
