package models;

import database.Order;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderModel implements BaseModel<Order>{

    @Override
    public ObservableList<Order> getAll() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        TypedQuery<Order> query = entityManager.createQuery("select o from Order o", Order.class);
        List<Order> orders1 = query.getResultList();
        entityManager.getTransaction().commit();

        orders.addAll(orders1);
        return orders;
    }
}
