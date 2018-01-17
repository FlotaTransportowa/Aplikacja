package database;

import javax.persistence.*;
import java.util.List;

@Entity
public class Track {
    @Id
    @GeneratedValue
    private long id;
    private boolean executed;
    private boolean assigned;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driverID")
    private Driver driver;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "machineID")
    private Machine machine;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "trackID")
    private List<Order> orders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
