package models;

import controllers.AddNotificationController;
import database.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class NotificationModel implements BaseModel{

    /**
     * Szuaka wszystkich zgłoszeń
     * @return Zwraca listę zgłoszeń
     */
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

    /**
     * Szuaka zgłoszeń typu podanego przez parametr
     * @param notifyType
     * @return Zwraca listę typów maszyn
     */
    public ObservableList<Notification> getAllOfType(AddNotificationController.NotifyType notifyType) {
        ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        try {
            TypedQuery<Notification> query = entityManager.createQuery("select e from Notification e where typeOfNotify= :type", Notification.class);
            query.setParameter("type", AddNotificationController.notifyTypeToString(notifyType));
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
