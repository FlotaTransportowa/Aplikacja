package fxModels;

import controllers.NotifyAccidentController;
import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.NotificationModel;

import static controllers.NotifyAccidentController.NotifyType.*;

public class NotificationFX {

    private NotifyAccidentController.NotifyType notifyType;
    private Notification notification;
    private long id;
    private java.util.Date date;
    private String description;
    private Notification.NotifyStatus status;
    private Employee employee;
    private Machine machine;

    private static NotificationModel notificationModel = new NotificationModel();
    private Boolean aBoolean;

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
    public static ObservableList<NotificationFX> getAllOfType(NotifyAccidentController.NotifyType notifyType){
        ObservableList<NotificationFX> notificationFXES = FXCollections.observableArrayList();
        ObservableList<Notification> notifications = null;

        notifications = notificationModel.getAllOfType(notifyType);
        if(notifications!=null) {
            for (Notification n : notifications) {
                notificationFXES.add(new NotificationFX(n));
            }
        }
        return notificationFXES;
    }

    public NotifyAccidentController.NotifyType getNotifyType() {
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
}
