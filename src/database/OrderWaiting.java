package database;

import javax.persistence.Entity;

@Entity
public class OrderWaiting extends OrderState{
    @Override
    public void edit(Order order) {
        //komunikat, że nie można edytować oczekującego zlecenia
    }
}