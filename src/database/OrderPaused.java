package database;

import org.controlsfx.control.StatusBar;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@Entity
public class OrderPaused extends OrderState{

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
        statusBar.setText("Zlecenie jest wstrzymane - jego zatwierdzenie jest niemożliwe.");
    }

    @Override
    public void removeOrder(Order order, StatusBar statusBar) {
        statusBar.setText("Zlecenie jest wstrzymane - jego usunięcie jest niemożliwe.");
    }

    @Override
    public void unconfirmOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Zlecenie jest wstrzymane - cofnięcie potwierdzenia jest niemożliwe.");
    }

    @Override
    public void takeOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void cancelOrder(Order toCancelOrder, EntityManager entityManager, StatusBar statusBar) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toCancelOrder.getId());
            order.setState(new OrderNotAssigned());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void pauseOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Zlecenie jest już wstrzymane.");
    }

    @Override
    public void unpauseOrder(Order toUnpauseOrder, EntityManager entityManager, StatusBar statusBar) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toUnpauseOrder.getId());
            order.setState(new OrderNotAssigned());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void finishOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Zlecenie jest wstrzymane - jego zakończenie jest niemożliwe.");
    }

    @Override
    public void postTheOrder(Order order, EntityManager entityManager) {

    }
}
