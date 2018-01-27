package database;

import javax.persistence.Entity;

@Entity
public class OrderPaused extends OrderState{
    @Override
    public void edit(Order order) {
        //komunikat, że nie można edytować wstrzymanego zlecenia
    }
}
