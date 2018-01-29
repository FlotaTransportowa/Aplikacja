package database;

import javax.persistence.*;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeOfNotify")
public abstract class Notification {
    public enum NotifyStatus {
        SENT
    }

    @Id
    @GeneratedValue
    private long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    private String description;
    private NotifyStatus status;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "machineID")
    private Machine machine;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeID")
    private Employee employee;

    public Notification(){
    }

    public Notification(Date date, String description, NotifyStatus status, Machine machine, Employee employee) {
        this.date = date;
        this.description = description;
        this.status = status;
        this.machine = machine;
        this.employee = employee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public NotifyStatus getStatus() {
        return status;
    }

    public void setStatus(NotifyStatus status) {
        this.status = status;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
