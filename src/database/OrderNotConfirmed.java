package database;

import javax.persistence.Entity;

@Entity
public class OrderNotConfirmed extends OrderState{
    @Override
    public void edit(Order order) {
        System.out.println("Wolno jemu edytować " + order.getType());
    }
}
