package fxModels;

import database.Driver;
import database.Machine;
import database.Order;
import database.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.TrackModel;

import java.util.List;

public class TrackFX {
    private long id;
    private String name;
    private boolean executed;
    private Driver driver;
    private Machine machine;
    private List<Order> orders;

    public static ObservableList<TrackFX> getAll(){
        ObservableList<TrackFX> trackFX = FXCollections.observableArrayList();
        ObservableList<Track> tracks = FXCollections.observableArrayList();
        TrackModel trackModel = new TrackModel();

        tracks = trackModel.getAll();

        for(Track t : tracks){
            trackFX.add(new TrackFX(t.getId(), t.getName(), t.isExecuted(), t.getDriver(), t.getMachine(), t.getOrders()));
        }

        return trackFX;
    }

    public static ObservableList<TrackFX> getAllNotAssigned(){
        ObservableList<TrackFX> trackFX = FXCollections.observableArrayList();
        ObservableList<Track> tracks = FXCollections.observableArrayList();
        TrackModel trackModel = new TrackModel();

        tracks = trackModel.getAllNotAssigned();

        for(Track t : tracks){
            trackFX.add(new TrackFX(t.getId(), t.getName(), t.isExecuted(), t.getDriver(), t.getMachine(), t.getOrders()));
        }

        return trackFX;
    }

    public static ObservableList<TrackFX> getAllDriverTracks(Driver driver){
        ObservableList<TrackFX> trackFX = FXCollections.observableArrayList();
        ObservableList<Track> tracks = FXCollections.observableArrayList();
        TrackModel trackModel = new TrackModel();

        tracks = trackModel.getAllDriverTracks(driver);

        for(Track t : tracks){
            trackFX.add(new TrackFX(t.getId(), t.getName(), t.isExecuted(), t.getDriver(), t.getMachine(), t.getOrders()));
        }

        return trackFX;
    }

    public TrackFX(long id, String name, boolean executed, Driver driver, Machine machine, List<Order> orders){
        this.id = id;
        this.name = name;
        this.executed = executed;
        this.driver = driver;
        this.machine = machine;
        this.orders = orders;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
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
