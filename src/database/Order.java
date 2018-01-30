package database;

import models.OrderModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Orders") //Okazuje się że w MySQL nie może być tabeli o nazwie Order ze względu na słowo kluczowe ORDER
public class Order{
    @Id
    @GeneratedValue
    private long id;
    private String title;
    private String type;
    @Temporal(TemporalType.DATE)
    private Date timeLimitForCompletion;
    private String comment;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressID", referencedColumnName = "id")
    private Address addressOfOrder;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "stateID")
    private OrderState orderState;
    @ManyToOne
    @JoinColumn(name = "trackID")
    private Track trackOfOrder;

    public OrderState getState() {
        return orderState;
    }

    public void setState(OrderState state) {
        OrderState tmpState = null;
        tmpState = OrderModel.retExistState(state);
        if(tmpState == null)
            this.orderState = state;
        else
            this.orderState = tmpState;
    }

    public long getId() {
        return id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Address getAddressOfOrder() {
        return addressOfOrder;
    }

    public void setAddressOfOrder(Address addressOfOrder) {
        this.addressOfOrder = addressOfOrder;
    }

    public Track getTrack() {
        return trackOfOrder;
    }

    public void setTrack(Track track) {
        this.trackOfOrder = track;
    }

    public Order(){}

    public Order(String title, String type, Date deadline, String comment, Address address){
        this.title = title;
        this.type = type;
        this.timeLimitForCompletion = deadline;
        this.comment = comment;
        this.addressOfOrder = address;
    }
}
