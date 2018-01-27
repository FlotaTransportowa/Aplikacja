package controllers;

import database.Machine;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import models.MachineModel;
import org.controlsfx.control.table.TableRowExpanderColumn;


public class ShowMachineController extends Controller{
    private LoggedController loggedController;

    @FXML private TableView<Machine> table;
    private static ObservableList<Machine> data;
    private MachineModel machineModel = new MachineModel();

    public void start() throws Exception{
        StackPane root = new StackPane();

        table = new TableView<>();
        table.setPrefWidth(500);

        TableRowExpanderColumn<Machine> expander = new TableRowExpanderColumn<>(this::createEditor);

        TableColumn<Machine, String> vin = new TableColumn<>("VIN");
        vin.setCellValueFactory(new PropertyValueFactory<>("VIN"));

        TableColumn<Machine, String> nrRej = new TableColumn<>("Numer rejestracyjnyyyyy");
        nrRej.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));

        table.getColumns().addAll(expander, vin, nrRej);
        table.setItems(initialize());

        root.getChildren().add(table);
    }

    private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<Machine> param){
        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.setHgap(10);
        editor.setVgap(5);

        Machine machine = param.getValue();
        TextField vinText = new TextField(machine.getVIN());
        TextField nrRejText = new TextField(machine.getRegistrationNumber());

        Button save = new Button("Zapisz");
        save.setOnAction(e -> {
            machine.setVIN(vinText.getText());
            machine.setRegistrationNumber(nrRejText.getText());
            param.toggleExpanded();
        });

        Button cancel = new Button("Anuluj");
        cancel.setOnAction(e1 -> param.toggleExpanded());

        editor.addRow(2, save, cancel);

        return editor;
    }

    @FXML
    public ObservableList<Machine> initialize() throws Exception {
        return FXCollections.observableArrayList(machineModel.getAll());
    }

    public void setLoggedController(LoggedController loggedController){
        this.loggedController = loggedController;
    }

}
