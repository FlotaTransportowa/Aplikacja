package controllers;

import database.OrderReport;
import javafx.fxml.FXML;
import database.Order;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;
import models.OrderReportModel;
import org.controlsfx.control.StatusBar;
import validation.Validation;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Kontroler do księgowania zleceń
 */
public class PostOrderController extends Controller{
    private LoggedController loggedController;
    Order order = null;
    private final String pattern = "yyyy-MM-dd";

    @FXML private DatePicker datePicker;
    @FXML private TextField kilometers, fuel, profit;
    @FXML private StatusBar statusBar;

    @FXML
    void initialize(){
        DatePicker checkIn = new DatePicker();
        datePicker.getEditor().setDisable(true);
        datePicker.setValue(LocalDate.now());
        checkIn.setValue(LocalDate.now().minusDays(1));
        datePicker.setShowWeekNumbers(true);
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

        datePicker.setConverter(converter);
        datePicker.setPromptText(pattern.toLowerCase());
        datePicker.requestFocus();

        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isAfter(
                                        checkIn.getValue().plusDays(1))
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };

        datePicker.setDayCellFactory(dayCellFactory);
        datePicker.setValue(LocalDate.now());
    }

    public void setOrder(Order o){
        order = o;
    }

    @FXML
    void postOrder(){
        if(!valid()) return;

        OrderReport orderReport = getFillOrderReport();

        OrderReportModel reportModel = new OrderReportModel();

        reportModel.pushToDatabase(orderReport);

        order.getState().postTheOrder(order, entityManager);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setGraphic(null);
        alert.setContentText("Wygenerowano raport. Zlecenie zostało zaksięgowane.");
        alert.showAndWait();

        removeTab();
    }

    @FXML private void removeTab()
    {
        loggedController.removeTab(super.getThisTab());
    }

    private OrderReport getFillOrderReport(){
        OrderReport orderReport = new OrderReport();
        LocalDate date = datePicker.getValue();
        Instant instant = Instant.from(date.atStartOfDay(ZoneId.systemDefault()));
        Date myDate = Date.from(instant);

        orderReport.setFuel(Double.parseDouble(fuel.getText()));
        orderReport.setDateDone(myDate);
        orderReport.setProfit(Double.parseDouble(profit.getText()));
        orderReport.setKilometers(Double.parseDouble(kilometers.getText()));
        orderReport.setDriver(order.getTrack().getDriver());
        orderReport.setMachine(order.getTrack().getMachine());
        orderReport.setOrder(order);

        return orderReport;
    }

    /**Walidacja poprawności dodawanego zlecenia
     * @return Zwraca prawdę, gdy zgłoszenie poprawne, fałsz w przeciwnym przypadku
     */
    private boolean valid(){
        boolean validateFlag = true;

        if(!Validation.isDouble(profit.getText()) || profit.getText().isEmpty()){
            profit.setStyle(nonValidStyle);
            validateFlag = false;
        } else profit.setStyle(validStyle);
        if(!Validation.isDouble(kilometers.getText()) || kilometers.getText().isEmpty()){
            kilometers.setStyle(nonValidStyle);
            validateFlag = false;
        } else kilometers.setStyle(validStyle);
        if(!Validation.isDouble(fuel.getText()) || fuel.getText().isEmpty()){
            fuel.setStyle(nonValidStyle);
            validateFlag = false;
        } else fuel.setStyle(validStyle);

        return validateFlag;
    }

    public LoggedController getLoggedController() {
        return this.loggedController;
    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController=loggedController;
    }
}
