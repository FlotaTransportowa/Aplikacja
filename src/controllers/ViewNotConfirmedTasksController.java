package controllers;

import database.Order;
import database.OrderNotAssigned;
import database.OrderNotConfirmed;
import database.OrderState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import manager.GlobalManager;
import models.OrderModel;
import org.controlsfx.control.StatusBar;
import validation.Validation;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ViewNotConfirmedTasksController extends Controller{
    @FXML private TextField searchField;
    @FXML private TableView<Order> notConfirmedTasksTable;
    @FXML private Pane editPane;
    private DatePicker checkIn;
    @FXML private DatePicker editTaskDate;
    @FXML private TextField editTaskTitle;
    @FXML private TextArea editTaskComment;
    @FXML private StatusBar statusBar;
    private final String pattern = "yyyy-MM-dd";
    private static ObservableList<Order> taskData;
    OrderModel orderModel = new OrderModel();

    private Order orderForEdit;

    @FXML
    void initialize(){
        refreshView();
        checkIn = new DatePicker();
        editTaskDate.getEditor().setDisable(true);
        editTaskDate.setValue(LocalDate.now());
        checkIn.setValue(LocalDate.now().minusDays(1));
        editTaskDate.setShowWeekNumbers(true);
        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };

        editTaskDate.setConverter(converter);
        editTaskDate.setPromptText(pattern.toLowerCase());
        editTaskDate.requestFocus();

        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(
                                        checkIn.getValue().plusDays(1))
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };

        editTaskDate.setDayCellFactory(dayCellFactory);
        editTaskDate.setValue(LocalDate.now());
    }

    @FXML
    void confirmSelectedTask(){
        Order toConfirmOrder = null;
        toConfirmOrder = notConfirmedTasksTable.getSelectionModel().getSelectedItem();
        if(toConfirmOrder == null) {
            statusBar.setText("Należy zaznaczyć zlecenie w tabeli...");
            return;
        }

        if(checkFields(toConfirmOrder)) {
            statusBar.setText("Nie wszystkie pola w wybranym zleceniu są wypełnione...");
            return;
        }

        Order order = entityManager.find(Order.class, toConfirmOrder.getId());
        order.setState(new OrderNotAssigned());
        statusBar.setText("Zatwierdzono zlecenie: " + order.getTitle());

        refreshView();
    }

    @FXML
    void saveChanges(){
        LocalDate date = editTaskDate.getValue();
        Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));

        Order order = null;
        try{
            entityManager.getTransaction().begin();
            order = entityManager.find(Order.class, orderForEdit.getId());
            order.setTitle(editTaskTitle.getText());
            order.setComment(editTaskComment.getText());
            order.setTimeLimitForCompletion(Date.from(instant));
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.refresh(order);
        }

        refreshView();
    }

    @FXML
    void editSelectedTask(){
        Order editOrder = null;
        editOrder = notConfirmedTasksTable.getSelectionModel().getSelectedItem();
        if(editOrder == null)
            return;
        editPane.setVisible(true);
        fillEditForm(editOrder);
        orderForEdit = editOrder;
    }

    @FXML
    void cancelEdit(){
        editPane.setVisible(false);
        editTaskDate.setValue(LocalDate.now());
        editTaskTitle.clear();
        editTaskComment.clear();
    }

    @FXML
    void fillEditForm(Order order){
        Date input = new Date();
        input.setTime(order.getTimeLimitForCompletion().getTime());
        LocalDate date = LocalDate.ofInstant(input.toInstant(), ZoneId.systemDefault());

        editTaskDate.setValue(date);
        editTaskTitle.setText(order.getTitle());
        editTaskComment.setText(order.getComment());
    }

    @FXML
    private void refreshView(){
        cancelEdit();
        editPane.setVisible(false);
        taskData = FXCollections.observableArrayList(orderModel.getAllNotConfirmed());
        notConfirmedTasksTable.setItems(taskData);
        notConfirmedTasksTable.refresh();
        setSearchField();
    }

    @FXML
    boolean checkFields(Order order){
        return order.getComment().isEmpty();
    }

    @FXML
    void setSearchField(){
        FilteredList<Order> filteredOrders = new FilteredList<Order>(taskData, p -> true);

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

        sortedData.comparatorProperty().bind(notConfirmedTasksTable.comparatorProperty());

        notConfirmedTasksTable.setItems(sortedData);
    }
}
