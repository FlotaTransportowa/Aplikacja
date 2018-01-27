package models;

import database.Order;
import database.OrderNotConfirmed;
import database.OrderState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class OrderModel implements BaseModel<Order>{

    @Override
    public ObservableList<Order> getAll() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        try {
            entityManager.getTransaction().begin();
            TypedQuery<Order> query = entityManager.createQuery("select o from Order o", Order.class);
            List<Order> orders1 = query.getResultList();
            orders.addAll(orders1);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return orders;
    }

    public ObservableList<Order> getAllAlone() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        try{
            entityManager.getTransaction().begin();
            TypedQuery<Order> query = entityManager.createQuery("select o from Order o where trackID = null", Order.class);
            List<Order> orders1 = query.getResultList();
            orders.addAll(orders1);
        } catch (Exception e){
            System.out.println("Brak zleceń bez trasy.");
        } finally {
            entityManager.getTransaction().commit();
        }

        return orders;
    }

    public ObservableList<Order> getAllNotConfirmed() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();
        OrderState state = OrderModel.retExistState(new OrderNotConfirmed());
        try{
            entityManager.getTransaction().begin();
            TypedQuery<Order> query = entityManager.createQuery("select o from Order o where stateID = :stateIdentifier", Order.class);
            query.setParameter("stateIdentifier", state.getId());
            List<Order> orders1 = query.getResultList();
            orders.addAll(orders1);
        } catch (Exception e){
        } finally {
            entityManager.getTransaction().commit();
        }

        return orders;
    }

    public static OrderState retExistState(OrderState state){
        OrderState orderState = null;
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        try {
            TypedQuery<OrderState> query = entityManager.createQuery("select o from OrderState o where state = :stateName", OrderState.class);
            query.setParameter("stateName", state.getClass().getSimpleName());
            orderState = query.getSingleResult();
        } catch (Exception e){
            System.out.println("Stan istnieje");
        } finally {
            entityManager.getTransaction().commit();
        }

        return orderState;
    }


}
