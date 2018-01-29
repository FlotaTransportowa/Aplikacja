package database;

import javax.persistence.Entity;
import javax.persistence.EntityManager;

@Entity
public class OrderWaiting extends OrderState{

    @Override
    public void edit(Order order) {

    }

    @Override
    public void assignOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void unassignOrder(Order orderToUnassign, EntityManager entityManager) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, orderToUnassign.getId());
            order.setState(new OrderNotAssigned());
        } catch (Exception e){
            e.printStackTrace();
        }
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
    public void takeOrder(Order toTakeOrder, EntityManager entityManager) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toTakeOrder.getId());
            order.setState(new OrderStillPerformed());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void cancelOrder(Order toCancelOrder, EntityManager entityManager) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toCancelOrder.getId());
            order.setState(new OrderCanceled());
        } catch (Exception e){
            e.printStackTrace();
        }
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
