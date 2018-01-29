package database;

import org.hibernate.annotations.DiscriminatorOptions;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "state")
public abstract class OrderState {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    @OneToMany(mappedBy = "orderState")
    List<Order> orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public abstract void edit(Order order);

    public abstract void removeOrder(Order order);

    public abstract void assignOrder(Order order, EntityManager entityManager);

    public abstract void unassignOrder(Order order, EntityManager entityManager);

    public abstract void confirmOrder(Order order, EntityManager entityManager);

    public abstract void unconfirmOrder(Order order, EntityManager entityManager);

    public abstract void takeOrder(Order order, EntityManager entityManager);

    public abstract void cancelOrder(Order order, EntityManager entityManager);

    public abstract void pauseOrder(Order order, EntityManager entityManager);

    public abstract void unpauseOrder(Order order, EntityManager entityManager);

    public abstract void finishOrder(Order order, EntityManager entityManager);

}
