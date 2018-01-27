package database;

import javax.persistence.Entity;

@Entity
public class OrderCanceled extends OrderState{
    @Override
    public void edit(Order order) {
        //komunikat, że nie można edytować anulowanego zlecenia
    }
}
