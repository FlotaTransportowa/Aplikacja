package database;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UserOrder") //Okazuje się że w MySQL nie może być tabeli o nazwie Order ze względu na słowo kluczowe ORDER
public class Order {
    @Id
    @GeneratedValue
    private long id;
    private String type;
    @Temporal(TemporalType.DATE)
    private Date timeLimitForCompletion;
    private String comment;
    @OneToOne
    @JoinColumn(name = "addressId")
    private Address address;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
