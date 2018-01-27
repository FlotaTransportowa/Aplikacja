package database;

import javax.persistence.Entity;

@Entity
public class OrderDone extends OrderState{
    @Override
    public void edit(Order order) {
        //komunikat, że nie można edytować wykonanego zlecenia
    }
}
