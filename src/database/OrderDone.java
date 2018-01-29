package database;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@Entity
public class OrderDone extends OrderState{
    @Override
    public void edit(Order order) {

    }

    @Override
    public void assignOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void unassignOrder(Order order, EntityManager entityManager) {

    }


    @Override
    public void confirmOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void removeOrder(Order order) {

    }

    @Override
    public void unconfirmOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void takeOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void cancelOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void pauseOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void unpauseOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void finishOrder(Order order, EntityManager entityManager) {

    }
}
