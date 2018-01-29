package database;

import javax.persistence.*;
import java.util.List;

@Entity
public class Track {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private boolean executed;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driverID", referencedColumnName = "id")
    private Driver driverOfTrack;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "machineID", referencedColumnName = "id")
    private Machine machineOfTrack;

    @OneToMany(mappedBy = "trackOfOrder", cascade = CascadeType.ALL)
    private List<Order> orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Driver getDriver() {
        return driverOfTrack;
    }

    public void setDriver(Driver driver) {
        this.driverOfTrack = driver;
    }

    public Machine getMachine() {
        return machineOfTrack;
    }

    public void setMachine(Machine machine) {
        this.machineOfTrack = machine;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
