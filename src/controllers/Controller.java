package controllers;

import database.*;
import fxModels.OrderFX;
import fxModels.TrackFX;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import manager.GlobalManager;
import org.controlsfx.control.table.TableRowExpanderColumn;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public abstract class Controller{
    @FXML
    protected MainController mainController;
    private Tab thisTab;
    public final String validStyle = "-fx-background-color:#fff; -fx-border-width: 1px ; -fx-border-color: #a8a8a8; -fx-border-radius: 2px;";
    public final String nonValidStyle = "-fx-background-color:#f9a7a7; -fx-border-width: 1px ; -fx-border-color: #a8a8a8; -fx-border-radius: 2px;";

    public static EntityManager entityManager = GlobalManager.getManager();

    public static Account account;

    void setMainController(MainController par)
    {
        this.mainController=par;
    }

    public Tab getThisTab() {
        return thisTab;
    }

    public void setThisTab(Tab thisTab) {
        this.thisTab = thisTab;
    }

    public GridPane createTrackExpander(TableRowExpanderColumn.TableRowDataFeatures<TrackFX> param) {

        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.getColumnConstraints().add(new ColumnConstraints(930));

        TableView<OrderFX> orderTable = new TableView<>();
        orderTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        orderTable.setMinHeight(200);
        orderTable.setMaxHeight(200);

        ObservableList<OrderFX> data;

        data = FXCollections.observableArrayList(OrderFX.getTrackOrders(param.getValue()));

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

        orderTable.setItems(data);

        editor.addRow(0, new Label("Zlecenia przynależne do trasy"));
        editor.addRow(1, orderTable);

        return editor;
    }

    public GridPane createOrderExpander(TableRowExpanderColumn.TableRowDataFeatures<OrderFX> param) {
        System.out.println("witam");
        GridPane editor = new GridPane();
        editor.setPadding(new Insets(10));
        editor.setHgap(2);
        editor.setVgap(6);

        int rowIndex = 0;
        editor.addRow(rowIndex, new Label("Adres"));
        editor.addRow(++rowIndex, new Label("Kod pocztowy: "), new Label(param.getValue().getPostalCode()));
        editor.addRow(++rowIndex, new Label("Miejscowość: "), new Label(param.getValue().getLocality()));
        editor.addRow(++rowIndex, new Label("Ulica: "), new Label(param.getValue().getStreet()));
        editor.addRow(++rowIndex, new Label("Numer domu: "), new Label(param.getValue().getApartmentNumer()));
        editor.addRow(++rowIndex, new Separator());
        editor.addRow(++rowIndex, new Label("Komentarz"));
        editor.addRow(++rowIndex, new Label(param.getValue().getComment()));
        editor.addRow(++rowIndex, new Separator());
        if(param.getValue().getTrack().getMachine() != null && param.getValue().getTrack().getDriver() != null){
            editor.addRow(++rowIndex, new Label("Przydzielona maszyna"));
            editor.addRow(++rowIndex, new Label(String.valueOf(param.getValue().getTrack().getMachine().getId())));
            editor.addRow(++rowIndex, new Label("Przydzielony kierowca"));
            editor.addRow(++rowIndex, new Label(String.valueOf(param.getValue().getTrack().getDriver())));
        }

        return editor;
    }


    public Order findOrder(OrderFX orderFX){
        Order order = null;
        try {
            entityManager.getTransaction().begin();
            TypedQuery<Order> query = entityManager.createQuery("select o from Order o where id = :identifier", Order.class);
            query.setParameter("identifier", orderFX.getId());
            order = query.getSingleResult();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }
        return order;
    }


}
