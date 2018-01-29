package database;

import org.controlsfx.control.StatusBar;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@Entity
public class OrderNotAssigned extends OrderState{
    @Override
    public void edit(Order order) {
        //komunikat, że nie można edytować zatwierdzonego zlecenia
    }

    @Override
    public void assignOrder(Order toAssignOrder, EntityManager entityManager) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toAssignOrder.getId());
            order.setState(new OrderWaiting());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void unassignOrder(Order order, EntityManager entityManager) {
    }

    @Override
    public void confirmOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Wybrane zlecenie jest już zatwierdzone.");
    }

    @Override
    public void removeOrder(Order order, StatusBar statusBar) {

    }

    @Override
    public void unconfirmOrder(Order orderToUnconfirm, EntityManager entityManager, StatusBar statusBar) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, orderToUnconfirm.getId());
            order.setState(new OrderNotConfirmed());
        } catch (Exception e){
            e.printStackTrace();
        }
        statusBar.setText("Cofnięto zatwierdzenie.");
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
    public void finishOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void postTheOrder(Order order, EntityManager entityManager) {

    }


}
