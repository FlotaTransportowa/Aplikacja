package fxModels;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.MachineModel;
import models.OrderModel;

import java.util.Date;

public class OrderFX {
    private long id;
    private String title;
    private String type;
    private Date timeLimitForCompletion;
    private String comment;
    private String postalCode;
    private String locality;
    private String street;
    private String apartmentNumer;
    private String orderState;
    private Track track;

    public OrderFX(long id, String title, String type, Date timeLimitForCompletion, String comment, String postalCode, String locality, String street, String apartmentNumer, String orderState, Track track) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.timeLimitForCompletion = timeLimitForCompletion;
        this.comment = comment;
        this.postalCode = postalCode;
        this.locality = locality;
        this.street = street;
        this.apartmentNumer = apartmentNumer;
        this.orderState = orderState;
        this.track = track;
    }

    public static ObservableList<OrderFX> getAll(){
        ObservableList<OrderFX> ordersFX = FXCollections.observableArrayList();
        ObservableList<Order> orders = FXCollections.observableArrayList();
        OrderModel orderModel = new OrderModel();

        orders = orderModel.getAll();

        for(Order t : orders){
            ordersFX.add(new OrderFX(t.getId(), t.getTitle(), t.getType(), t.getTimeLimitForCompletion(), t.getComment(), t.getAddressOfOrder().getPostalCode(),
                    t.getAddressOfOrder().getLocality(), t.getAddressOfOrder().getStreet(), t.getAddressOfOrder().getApartmentNumber(), t.getOrderState().getName(), t.getTrack()));
        }

        return ordersFX;
    }
    public static ObservableList<OrderFX>  getEmployeeOrders(Employee employee) {
        ObservableList<OrderFX> ordersFX = FXCollections.observableArrayList();
        ObservableList<Order> orders = FXCollections.observableArrayList();
        OrderModel orderModel = new OrderModel();

        orders = orderModel.getEmployeeOrders(employee);

        for(Order t : orders){
            ordersFX.add(new OrderFX(t.getId(), t.getTitle(), t.getType(), t.getTimeLimitForCompletion(), t.getComment(), t.getAddressOfOrder().getPostalCode(),
                    t.getAddressOfOrder().getLocality(), t.getAddressOfOrder().getStreet(), t.getAddressOfOrder().getApartmentNumber(), t.getOrderState().getName(), t.getTrack()));
        }

        return ordersFX;
    }
    public static ObservableList<OrderFX> getTrackOrders(TrackFX track){
        ObservableList<OrderFX> ordersFX = FXCollections.observableArrayList();
        ObservableList<Order> orders = FXCollections.observableArrayList();
        OrderModel orderModel = new OrderModel();

        orders = orderModel.getTrackOrders(track.getId());

        for(Order t : orders){
            ordersFX.add(new OrderFX(t.getId(), t.getTitle(), t.getType(), t.getTimeLimitForCompletion(), t.getComment(), t.getAddressOfOrder().getPostalCode(),
                    t.getAddressOfOrder().getLocality(), t.getAddressOfOrder().getStreet(), t.getAddressOfOrder().getApartmentNumber(), t.getOrderState().getName(), t.getTrack()));
        }

        return ordersFX;
    }

    public static ObservableList<OrderFX> getTrackOrdersWithoutCanceledAndDone(TrackFX track){
        ObservableList<OrderFX> ordersFX = FXCollections.observableArrayList();
        ObservableList<Order> orders = FXCollections.observableArrayList();
        OrderModel orderModel = new OrderModel();

        orders = orderModel.getTrackOrdersWithoutCanceledAndDone(track.getId());

        for(Order t : orders){
            ordersFX.add(new OrderFX(t.getId(), t.getTitle(), t.getType(), t.getTimeLimitForCompletion(), t.getComment(), t.getAddressOfOrder().getPostalCode(),
                    t.getAddressOfOrder().getLocality(), t.getAddressOfOrder().getStreet(), t.getAddressOfOrder().getApartmentNumber(), t.getOrderState().getName(), t.getTrack()));
        }

        return ordersFX;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimeLimitForCompletion() {
        return timeLimitForCompletion;
    }

    public void setTimeLimitForCompletion(Date timeLimitForCompletion) {
        this.timeLimitForCompletion = timeLimitForCompletion;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartmentNumer() {
        return apartmentNumer;
    }

    public void setApartmentNumer(String apartmentNumer) {
        this.apartmentNumer = apartmentNumer;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }


}
