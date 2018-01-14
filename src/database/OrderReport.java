package database;

import javax.persistence.*;
import java.util.Date;

@Entity
public class OrderReport {
    @Id
    @GeneratedValue
    private long id;
    private Date date;
    private double fuel;
    private double kilometers;
    private double profit;
    @OneToOne
    @JoinColumn(name = "driverId")
    private Driver driver;
    @OneToOne
    @JoinColumn(name = "orderId")
    private Order order;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}
