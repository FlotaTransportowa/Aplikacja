package database;

import javax.persistence.Entity;

@Entity
public class OrderStillPerformed extends OrderState{
    @Override
    public void edit(Order order) {
        //komunikat, że nie można edytować aktualnie wykonywanego zlecenia
    }
}
