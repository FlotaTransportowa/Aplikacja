package fxModels;

import controllers.NotifyAccidentController;
import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.NotificationModel;

import java.sql.Date;

import static controllers.NotifyAccidentController.NotifyType.*;

public class NotificationFX {

    private NotifyAccidentController.NotifyType notifyType;
    private Notification notification;
    private static NotificationModel notificationModel = new NotificationModel();
    private boolean aBoolean;

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
    }

    public static ObservableList<NotificationFX> getAll(){
        ObservableList<NotificationFX> notificationFXES = FXCollections.observableArrayList();
        ObservableList<Notification> notifications = FXCollections.observableArrayList();

        notifications = notificationModel.getAll();

        for(Notification n : notifications){
            notificationFXES.add(new NotificationFX(n));
        }

        return notificationFXES;
    }


    public NotifyAccidentController.NotifyType getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(NotifyAccidentController.NotifyType notifyType) {
        this.notifyType = notifyType;
    }
    public long getId() {
        return notification.getId();
    }

    public void setId(long id) {
        notification.setId(id);
    }

    public String getDescription() {
        return notification.getDescription();
    }

    public void setDescription(String description) {
        notification.setDescription(description);
    }

    public String getStatus() {
        return notification.getStatus();
    }

    public void setStatus(String status) {
        notification.setStatus(status);
    }

    public Machine getMachine() {
       return notification.getMachine();
    }

    public void setMachine(Machine machine) {
        notification.setMachine(machine);
    }

    public Employee getEmployee() {
        return notification.getEmployee();
    }

    public void setEmployee(Employee employee) {
        notification.setEmployee(employee);
    }

    public java.util.Date getDate() {
        return notification.getDate();
    }

    public void setDate(java.util.Date date) {
        notification.setDate(date);
    }

    public boolean isaBoolean() {
        return aBoolean;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }
}
