package database;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
public class NotifyAccident extends Notification{
    private boolean ifCollision;

    public NotifyAccident(Date date, String description, String status, Machine machine, Employee employee, boolean ifCollision) {
        super(date, description, status, machine, employee);
        this.ifCollision = ifCollision;
    }

    public boolean isIfCollision() {
        return ifCollision;
    }

    public void setIfCollision(boolean ifCollision) {
        this.ifCollision = ifCollision;
    }
}
