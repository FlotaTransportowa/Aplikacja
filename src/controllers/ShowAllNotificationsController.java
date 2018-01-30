package controllers;

import database.Employee;
import database.Machine;
import database.Notification;
import fxModels.NotificationFX;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

public class ShowAllNotificationsController extends Controller {
    @FXML
    private TextField searchField;
    @FXML private TableView<NotificationFX> tableView;
    private static ObservableList<NotificationFX> data;

    private PermissionAccordionController permissionAccordionController;
    private AddNotificationController.NotifyType notifyType;
    private FilteredList<NotificationFX> notificationFXES;
    private boolean initializing;

    @FXML
    private void initialize()
    {
        initializing=true;
        TableColumn<NotificationFX,Date> dateCol = new TableColumn<>("Data");
        dateCol.setCellValueFactory(new PropertyValueFactory("date"));
        TableColumn<NotificationFX,String> descriptionCol = new TableColumn<>("Opis");
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<NotificationFX, Employee> employeeCol = new TableColumn<>("Zgłaszający");
        employeeCol.setCellValueFactory(new PropertyValueFactory<>("employee"));
        TableColumn<NotificationFX, Machine> machineCol = new TableColumn<>("Maszyna");
        machineCol.setCellValueFactory(new PropertyValueFactory<>("machine"));

        TableColumn<NotificationFX, Notification.NotifyStatus> notifyStatusCol = new TableColumn<>("Status zgłoszenia");
        notifyStatusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        TableColumn<NotificationFX, ButtonBar> buttonBarCol = new TableColumn<>("Akcja");
        buttonBarCol.setCellValueFactory(new PropertyValueFactory<>("buttonBar"));

        tableView.getColumns().addAll(dateCol,descriptionCol,employeeCol,machineCol,notifyStatusCol,buttonBarCol);

//        TableRowExpanderColumn<NotificationFX> expander = new TableRowExpanderColumn<NotificationFX>(this::createTrackExpander);
//        expander.setMinWidth(30);
//        expander.setMaxWidth(30);
    }

    /**
     * Ustawienie danych w widoku
     */
    public void initNofity()
    {
        data=NotificationFX.getAllOfType(notifyType,this);
        if(initializing)
            booleanChoiceInit();
        tableView.setItems(data);
        initSearchField();
    }

    private void initSearchField()
    {
        notificationFXES = new FilteredList<NotificationFX>(data,p->true);
        searchField.textProperty().addListener((observable, oldValue, newValue) ->{
            notificationFXES.setPredicate(notificationFX -> {
                if(newValue == null || newValue.isEmpty())
                    return true;

                    String lowerCaseFilter = newValue.toLowerCase();

                    if(notificationFX.getDescription().toLowerCase().contains(lowerCaseFilter)
                            ||notificationFX.getEmployee().toString().toLowerCase().contains(lowerCaseFilter)
                            ||notificationFX.getMachine().toString().toLowerCase().contains(lowerCaseFilter)
                            ||notificationFX.getDate().toString().toLowerCase().contains(lowerCaseFilter)
                            ||notificationFX.getStatus().toString().toLowerCase().contains(lowerCaseFilter)
                            )
                        return true;
                    else
                        return false;
            });
        });
        SortedList<NotificationFX> sortedList = new SortedList<>(notificationFXES);

        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    private void booleanChoiceInit()
    {
        switch (notifyType){
            case THEFT:
                TableColumn<NotificationFX, Boolean> victimsCol = new TableColumn<>("Ofiary");
                victimsCol.setCellValueFactory(new PropertyValueFactory<>("aBoolean"));
                tableView.getColumns().add(victimsCol);
                break;
            case DEFECT:
                break;
            case ACCIDENT:TableColumn<NotificationFX, Boolean> collision = new TableColumn<>("Kolizja");
                collision.setCellValueFactory(new PropertyValueFactory<>("aBoolean"));
                tableView.getColumns().add(collision);
                break;
        }

        initializing=false;
    }

    public void setPermissionAccordionController(PermissionAccordionController permissionAccordionController) {
        this.permissionAccordionController = permissionAccordionController;
    }

    public void setNotifyType(AddNotificationController.NotifyType notifyType) {
        this.notifyType = notifyType;
    }

}
