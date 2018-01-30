package controllers;


import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import models.MachineModel;
import models.NotificationModel;

import java.sql.Date;
import java.util.Calendar;

public class AddNotificationController extends Controller {

    private static NotificationModel notificationModel = new NotificationModel();

    @FXML private ChoiceBox machineChoiceBox;
    @FXML private TextArea descriptionTextArea;

    @FXML private VBox boolChoice;
    @FXML private RadioButton yesRadio;
    @FXML private RadioButton noRadio;
    @FXML private Label notifyLabel;

    private static MachineModel machineModel = new MachineModel();
    private ObservableList<Machine> machineObservableList;
    private PermissionAccordionController permissionAccordionController;
    private Date date;
    public enum NotifyType{
        DEFECT,THEFT,ACCIDENT
    }

    /**
     * Ustawia typ zgłoszenia
     * @param notifyType
     * @return
     */
    public static String notifyTypeToString(NotifyType notifyType)
    {
        String value = new String();
        switch (notifyType){
            case THEFT:
                value = "NotifyTheft";
                break;
            case DEFECT:
                value = "NotifyDefect";
                break;
            case ACCIDENT:
                value = "NotifyAccident";
                break;
        }
        return value;
    }
    private NotifyType notifyType;

    public AddNotificationController()
    {
        date = new Date(Calendar.getInstance().getTime().getTime());
    }

    @FXML
    void initialize()
    {
        machineObservableList = machineModel.getAll();
        machineChoiceBox.setItems(FXCollections.observableArrayList(machineObservableList));
    }
    @FXML private void removeTab()
    {
        permissionAccordionController.getLoggedController().removeTab(super.getThisTab());
    }
    public void initNofity()
    {
        booleanChoiceInit();
    }

    /**
     * Dodanie zgłoszenia do bazy
     */
    @FXML
    private void pushToDatabase()
    {
        Notification notification = createNotification();
        notificationModel.pushToDatabase(notification);
    }

    private Notification createNotification()
    {
        String description = descriptionTextArea.getText();
        Notification.NotifyStatus status = Notification.NotifyStatus.SENT;
        Machine machine = (Machine) machineChoiceBox.getSelectionModel().getSelectedItem();
        machine.setBusy(true);
        Employee employee = permissionAccordionController.getLoggedController().getLoggedEmployee();
        switch (notifyType){
            case ACCIDENT:

                return new NotifyAccident(date, description, status, machine, employee,yesRadio.isSelected());
            case DEFECT:
                return new NotifyDefect(date, description, status, machine, employee);
            case THEFT:
                return new NotifyTheft(date, description, status, machine, employee, yesRadio.isSelected());
        }
        return null;
    }

    /**
     * Ustawienie widoku w zależności od typu zgłoszenia
     */
    private void booleanChoiceInit()
    {
        switch (notifyType){
            case THEFT:
                notifyLabel.setText("Ofiary");
                boolChoice.setVisible(true);
                break;
            case DEFECT:
                boolChoice.setVisible(false);
                break;
            case ACCIDENT:
                notifyLabel.setText("Kolizja");
                boolChoice.setVisible(true);
                break;
        }
    }
    public PermissionAccordionController getPermissionAccordionController() {
        return permissionAccordionController;
    }

    public void setPermissionAccordionController(PermissionAccordionController permissionAccordionController) {
        this.permissionAccordionController = permissionAccordionController;
    }

    public NotifyType getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(NotifyType notifyType) {
        this.notifyType = notifyType;
    }
}

