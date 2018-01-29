package models;

import controllers.NotifyAccidentController;
import database.Employee;
import database.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class NotificationModel implements BaseModel{

    @Override
    public ObservableList getAll() {
        ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        try {
            TypedQuery<Notification> query = entityManager.createQuery("select e from Notification e", Notification.class);
            List<Notification> notifications = query.getResultList();
            notificationObservableList.addAll(notifications);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
            return notificationObservableList;
        }
    }

    public ObservableList<Notification> getAllOfType(NotifyAccidentController.NotifyType notifyType) {
        ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        try {
            TypedQuery<Notification> query = entityManager.createQuery("select e from Notification e where typeOfNotify= :type", Notification.class);
            query.setParameter("type",NotifyAccidentController.notifyTypeToString(notifyType));
            List<Notification> notifications = query.getResultList();
            notificationObservableList.addAll(notifications);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
            return notificationObservableList;
        }
    }
}
