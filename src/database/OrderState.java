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
    @OneToMany(mappedBy = "orderState")
    List<Order> orders;

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
}
