package database;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Reports")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeOfReport")
public abstract class Report {
    @Id
    @GeneratedValue
    private long id;
    private String title;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String description;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
