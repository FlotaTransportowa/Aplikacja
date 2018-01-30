package controllers;

import database.OrderReport;
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
import models.OrderReportModel;
import org.controlsfx.control.table.TableRowExpanderColumn;
import validation.Validation;

public class ShowOrderReportsController extends Controller{
    private LoggedController loggedController;

    @FXML private TableView<OrderReport> tableOrderReport;
    private ObservableList<OrderReport> reportData;
    @FXML private TextField searchField;
    OrderReportModel model = new OrderReportModel();

    @FXML
    void initialize(){
        TableRowExpanderColumn<OrderReport> expander = new TableRowExpanderColumn<OrderReport>(this::createOrderReportExpander);
        expander.setMinWidth(30);
        expander.setMaxWidth(30);

        TableColumn<OrderReport, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<OrderReport, String> dateCol = new TableColumn<>("Data wykonania");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateDone"));

        TableColumn<OrderReport, String> fuelCol = new TableColumn<>("Zużyte paliwo");
        fuelCol.setCellValueFactory(new PropertyValueFactory<>("fuel"));

        TableColumn<OrderReport, String> kilometersCol = new TableColumn<>("Ilość kilometrów");
        kilometersCol.setCellValueFactory(new PropertyValueFactory<>("kilometers"));

        TableColumn<OrderReport, String> profitCol = new TableColumn<>("Zysk");
        profitCol.setCellValueFactory(new PropertyValueFactory<>("profit"));

        tableOrderReport.getColumns().addAll(expander, idCol, dateCol, fuelCol, kilometersCol, profitCol);


        reportData = FXCollections.observableArrayList(model.getAll());

        tableOrderReport.setItems(reportData);
        tableOrderReport.setPlaceholder(new Label("Brak raportów z wykonanych tras."));

        setSearchField();

    }

    @FXML
    void setSearchField() {
        FilteredList<OrderReport> filteredOrders = new FilteredList<OrderReport>(reportData, p -> true);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredOrders.setPredicate(report -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();

                if (Validation.isInteger(newValue)) {
                    if (report.getId() == Integer.parseInt(newValue.toLowerCase()))
                        return true;
                } else {
                    if (Validation.isDouble(newValue)) {
                       if(report.getProfit() == Double.parseDouble(newValue.toLowerCase()))
                           return true;
                       else if(report.getFuel() == Double.parseDouble(newValue.toLowerCase()))
                           return true;
                       else if(report.getKilometers() == Double.parseDouble(newValue.toLowerCase()))
                           return true;
                    }
                }
                return false;
            });
        });

        SortedList<OrderReport> sortedData = new SortedList<>(filteredOrders);

        sortedData.comparatorProperty().bind(tableOrderReport.comparatorProperty());

        tableOrderReport.setItems(sortedData);
    }

    public GridPane createOrderReportExpander(TableRowExpanderColumn.TableRowDataFeatures<OrderReport> param) {
        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.setHgap(2);
        editor.setVgap(6);

        OrderReport orderReport = param.getValue();

        int rowIndex = 0;
        editor.addRow(rowIndex, new Label("Zlecenie: " + orderReport.getOrder().getTitle() + " o identyfikatorze " + orderReport.getOrder().getId()));
        editor.addRow(++rowIndex, new Label("Wykonał: " + orderReport.getDriver().getFirstName() + " " + orderReport.getDriver().getLastName() +" ["+ orderReport.getDriver().getId() + "]"));
        editor.addRow(++rowIndex, new Label("Przy użyciu maszyny: " + orderReport.getMachine().getType().getMark() + " " + orderReport.getMachine().getType().getModel() +" ["+ orderReport.getMachine().getRegistrationNumber() + "]"));

        return editor;
    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController = loggedController;
    }
}
