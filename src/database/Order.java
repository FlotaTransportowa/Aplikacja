package database;

import org.hibernate.annotations.JoinColumnOrFormula;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders") //Okazuje się że w MySQL nie może być tabeli o nazwie Order ze względu na słowo kluczowe ORDER
public class Order {
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
}
