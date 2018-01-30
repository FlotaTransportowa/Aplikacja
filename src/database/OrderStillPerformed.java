package database;

import org.controlsfx.control.StatusBar;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@Entity
public class OrderStillPerformed extends OrderState{

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
        statusBar.setText("Zlecenie jest aktualnie wykonywane.");
    }

    @Override
    public void removeOrder(Order order, StatusBar statusBar) {
        statusBar.setText("Zlecenie nie może zostać usunięte. Zlecenie jest aktualnie wykonywane.");
    }

    @Override
    public void unconfirmOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Cofnięcie potwierdzenia zlecenia jest niemożliwe. Zlecenie jest aktualnie wykonywane.");
    }

    @Override
    public void takeOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void cancelOrder(Order toCancelOrder, EntityManager entityManager, StatusBar statusBar) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toCancelOrder.getId());
            order.setState(new OrderCanceled());
        } catch (Exception e){
            e.printStackTrace();
        }
        statusBar.setText("Zlecenie zostało anulowane.");
    }

    @Override
    public void pauseOrder(Order toPauseOrder, EntityManager entityManager, StatusBar statusBar) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toPauseOrder.getId());
            order.setState(new OrderPaused());
        } catch (Exception e){
            e.printStackTrace();
        }
        statusBar.setText("Wykonywanie zlecenia zostało wstrzymane.");
    }

    @Override
    public void unpauseOrder(Order order, EntityManager entityManager, StatusBar statusBar) {
        statusBar.setText("Nie można wznowić zlecenia. Zlecenie jest aktualnie wykonywane.");
    }

    @Override
    public void finishOrder(Order toFinishOrder, EntityManager entityManager, StatusBar statusBar) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toFinishOrder.getId());
            order.setState(new OrderDone());
        } catch (Exception e){
            e.printStackTrace();
        }
        statusBar.setText("Pomyślnie zakończono zlecenie.");
    }

    @Override
    public void postTheOrder(Order order, EntityManager entityManager) {

    }
}
