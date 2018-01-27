package database;

import javax.persistence.Entity;

@Entity
public class OrderNotAssigned extends OrderState{
    @Override
    public void edit(Order order) {
        //komunikat, że nie można edytować zatwierdzonego zlecenia
    }
}
