package database;

import org.controlsfx.control.StatusBar;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@Entity
public class OrderCanceled extends OrderState{

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
    public void confirmOrder(Order order, EntityManager entityManager, StatusBar statusBar) {

    }

    @Override
    public void removeOrder(Order order, StatusBar statusBar) {

    }

    @Override
    public void unconfirmOrder(Order order, EntityManager entityManager, StatusBar statusBar) {

    }

    @Override
    public void takeOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void cancelOrder(Order order, EntityManager entityManager, StatusBar statusBar) {

    }

    @Override
    public void pauseOrder(Order order, EntityManager entityManager, StatusBar statusBar) {

    }

    @Override
    public void unpauseOrder(Order order, EntityManager entityManager, StatusBar statusBar) {

    }

    @Override
    public void finishOrder(Order order, EntityManager entityManager, StatusBar statusBar) {

    }

    @Override
    public void postTheOrder(Order order, EntityManager entityManager) {

    }
}
