package controllers;

import database.Permission;
import fxModels.DriverWithPermsFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.controlsfx.control.table.TableRowExpanderColumn;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AssignTrackController {

    @FXML private TextField machineSearch, employeeSearch, trackSearch;
    @FXML private TableView<DriverWithPermsFX> driverWithPermsTable;
    @FXML private ObservableList<DriverWithPermsFX> data;
    @FXML private GridPane gridPane;

    @FXML
    private void initialize(){
        data = FXCollections.observableArrayList(DriverWithPermsFX.getAll());

        TableRowExpanderColumn<DriverWithPermsFX> expander = new TableRowExpanderColumn<DriverWithPermsFX>(this::createEditor);
        expander.setMinWidth(30);
        expander.setMaxWidth(30);
        //obrazek
//        Image image = new Image(getClass().getResourceAsStream("downPointerLabel.jpg"));
//        expander.setGraphic(new ImageView(image));

        TableColumn<DriverWithPermsFX, Long> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("employeeID"));

        TableColumn<DriverWithPermsFX, String> nameCol = new TableColumn<>("Imię");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<DriverWithPermsFX, String> lastNameCol = new TableColumn<>("Nazwisko");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<DriverWithPermsFX, String> typeCol = new TableColumn<>("Stanowisko");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<DriverWithPermsFX, String> ageCol = new TableColumn<>("Wiek");
        ageCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        driverWithPermsTable.getColumns().addAll(expander,idCol,nameCol,lastNameCol,typeCol,ageCol);

        driverWithPermsTable.setItems(data);

    }

    private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<DriverWithPermsFX> param) {

        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.setHgap(10);
        editor.setVgap(5);

        List<Permission> perms = param.getValue().getPerms();

        int rowCounter = 0;
        for (int i = 0; i < perms.size(); i++) {
            editor.addRow(rowCounter, new Label("Nazwa: "), new Label(perms.get(i).getName()));
            rowCounter++;
            editor.addRow(rowCounter, new Label("Opis: "), new Label(perms.get(i).getDescription()));
            rowCounter++;
            editor.addRow(rowCounter, new Separator());
            rowCounter++;
        }

        if(rowCounter == 0)
            editor.addRow(2, new Label("Pracownik nie posiada żadnych uprawnień."), new Label(""));

        return editor;
    }
}
