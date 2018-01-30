package database;

import manager.GlobalManager;
import org.controlsfx.control.StatusBar;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@Entity
public class OrderNotConfirmed extends OrderState{
    @Override
    public void edit(Order orderForEdit) {
        EntityManager entityManager = GlobalManager.getManager();
        Order order = null;
        try{
            entityManager.getTransaction().begin();
            order = entityManager.find(Order.class, orderForEdit.getId());
            order.setTitle(orderForEdit.getTitle());
            order.setComment(orderForEdit.getComment());
            order.setTimeLimitForCompletion(orderForEdit.getTimeLimitForCompletion());
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.refresh(order);
        }
    }

    @Override
    public void assignOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void unassignOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void confirmOrder(Order toConfirmOrder, EntityManager entityManager, StatusBar statusBar) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toConfirmOrder.getId());
            order.setState(new OrderNotAssigned());
        } catch (Exception e){
            e.printStackTrace();
        }
        statusBar.setText("Zatwierdzono zlecenie: " + order.getTitle());
    }

    @Override
    public void removeOrder(Order toRemoveOrder, StatusBar statusBar) {
        EntityManager entityManager = GlobalManager.getManager();
        try{
            entityManager.getTransaction().begin();
            entityManager.remove(toRemoveOrder);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }
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
