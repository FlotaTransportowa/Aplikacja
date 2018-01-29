package database;

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
    public void pauseOrder(Order toPauseOrder, EntityManager entityManager) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toPauseOrder.getId());
            order.setState(new OrderPaused());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void unpauseOrder(Order order, EntityManager entityManager) {

    }

    @Override
    public void finishOrder(Order toFinishOrder, EntityManager entityManager) {
        Order order = null;
        try{
            order = entityManager.find(Order.class, toFinishOrder.getId());
            order.setState(new OrderDone());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
