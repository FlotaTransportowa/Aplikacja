package database;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OrderReport {
    @Id
    @GeneratedValue
    private long id;
    @Temporal(TemporalType.DATE)
    private Date dateDone;
    private double fuel;
    private double kilometers;
    private double profit;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driverID")
    private Driver driver;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orderID")
    private Order order;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "machineID")
    private Machine machine;

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDateDone() {
        return dateDone;
    }

    public void setDateDone(Date dateDone) {
        this.dateDone = dateDone;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public double getKilometers() {
        return kilometers;
    }

    public void setKilometers(double kilometers) {
        this.kilometers = kilometers;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

}
