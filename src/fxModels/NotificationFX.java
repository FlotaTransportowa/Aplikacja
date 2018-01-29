package fxModels;

import controllers.AddNotificationController;
import controllers.ShowAllNotificationsController;
import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import manager.GlobalManager;
import models.NotificationModel;

import static controllers.AddNotificationController.NotifyType.*;

public class NotificationFX {

    private AddNotificationController.NotifyType notifyType;
    private Notification notification;
    private long id;
    private java.util.Date date;
    private String description;
    private Notification.NotifyStatus status;
    private Employee employee;
    private Machine machine;
    private ButtonBar buttonBar;
    private Button actionButton1, actionButton2;

    private static NotificationModel notificationModel = new NotificationModel();
    private Boolean aBoolean;
    private ShowAllNotificationsController showAllNotificationController;

    public NotificationFX(Notification notification) {
        switch (notification.getClass().getSimpleName().toString()){
            case "NotifyTheft":
                this.notifyType = THEFT;
                aBoolean = ((NotifyTheft) notification).isIfVictims();
            break;
            case "NotifyAccident":
                this.notifyType = ACCIDENT;
                aBoolean = ((NotifyAccident) notification).isIfCollision();
                break;
            case "NotifyDefect":
                this.notifyType=DEFECT;
                break;
        }

        this.notification = notification;
        decompositeValues();
        buttonBar = new ButtonBar();
        switch (status)
        {
            case SENT:
                actionButton1=new Button("Zatwierdź");
                actionButton2=new Button("Odrzuć");
                actionButton1.setOnAction(e->{
                    notifyAccept();
                    this.showAllNotificationController.initNofity();
                });
                actionButton2.setOnAction(e->{
                    notifyReject();
                    this.showAllNotificationController.initNofity();
                });
                buttonBar.getButtons().addAll(actionButton1,actionButton2);
                break;
            case ACCEPTED:
                actionButton1=new Button("Usuń");
                actionButton1.setOnAction(e->{
                    GlobalManager.remove(notification);
                    this.showAllNotificationController.initNofity();
                });
                buttonBar.getButtons().add(actionButton1);
                break;
            case REJECTED:
                actionButton1=new Button("Usuń");
                actionButton1.setOnAction(e->{
                    GlobalManager.remove(notification);
                    this.showAllNotificationController.initNofity();
                });
                buttonBar.getButtons().add(actionButton1);
                break;
        }


    }

    private void notifyReject() {
        this.notification.setStatus(Notification.NotifyStatus.REJECTED);
        this.machine.setBusy(false);
    }

    private void notifyAccept() {
        this.notification.setStatus(Notification.NotifyStatus.ACCEPTED);
        this.machine.setBusy(false);
        this.machine.setEfficient(false);
    }

    private void decompositeValues() {
        id=notification.getId();
        date=notification.getDate();
        description=notification.getDescription();
        status=notification.getStatus();
        employee=notification.getEmployee();
        machine=notification.getMachine();
    }

    public static ObservableList<NotificationFX> getAll(){
        ObservableList<NotificationFX> notificationFXES = FXCollections.observableArrayList();
        ObservableList<Notification> notifications = null;

        notifications = notificationModel.getAll();
        if(notifications!=null) {
            for (Notification n : notifications) {
                notificationFXES.add(new NotificationFX(n));
            }
        }
        return notificationFXES;
    }
    public static ObservableList<NotificationFX> getAllOfType(AddNotificationController.NotifyType notifyType, ShowAllNotificationsController showAllNotificationsController){
        ObservableList<NotificationFX> notificationFXES = FXCollections.observableArrayList();
        ObservableList<Notification> notifications = null;

        notifications = notificationModel.getAllOfType(notifyType);
        if(notifications!=null) {
            for (Notification n : notifications) {
                NotificationFX notificationFX = new NotificationFX(n);
                notificationFX.setShowAllNotificationController(showAllNotificationsController);
                notificationFXES.add(notificationFX);
            }
        }
        return notificationFXES;
    }

    private void setShowAllNotificationController(ShowAllNotificationsController showAllNotificationsController) {
        this.showAllNotificationController = showAllNotificationsController;
    }

    public AddNotificationController.NotifyType getNotifyType() {
        return notifyType;
    }

    public Notification getNotification() {
        return notification;
    }

    public long getId() {
        return id;
    }

    public java.util.Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Notification.NotifyStatus getStatus() {
        return status;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Machine getMachine() {
        return machine;
    }

    public static NotificationModel getNotificationModel() {
        return notificationModel;
    }

    public boolean getABoolean() {
        return aBoolean;
    }

    public ButtonBar getButtonBar() {
        return buttonBar;
    }
}
