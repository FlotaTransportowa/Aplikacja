package database;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
public class NotifyTheft extends Notification{
    private boolean ifVictims;

    public NotifyTheft() {
        super();

    }

    public NotifyTheft(Date date, String description, NotifyStatus status, Machine machine, Employee employee, boolean ifVictims) {
        super(date, description, status, machine, employee);
        this.ifVictims = ifVictims;
    }

    public boolean isIfVictims() {
        return ifVictims;
    }

    public void setIfVictims(boolean ifVictims) {
        this.ifVictims = ifVictims;
    }
}
