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
        statusBar.setText("Zlecenie jest anulowane - jego zatwierdzenie jest niemożliwe.");
    }

    @Override
    public void removeOrder(Order order, StatusBar statusBar) {
        statusBar.setText("Zlecenie jest anulowane - jego usunięcie jest niemożliwe.");
    }

    @Override
    public void unconfirmOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Zlecenie jest anulowane - cofnięcie potwierdzenia jest niemożliwe.");
    }

    @Override
    public void takeOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void cancelOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Zlecenie jest już anulowane");
    }

    @Override
    public void pauseOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Zlecenie jest anulowane - jego wstrzymanie jest niemożliwe.");
    }

    @Override
    public void unpauseOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Zlecenie jest anulowane - jego wznowienie jest niemożliwe.");
    }

    @Override
    public void finishOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Zlecenie jest anulowane - jego zakończenie jest niemożliwe.");
    }

    @Override
    public void postTheOrder(Order order, EntityManager entityManager) {
        
    }
}
