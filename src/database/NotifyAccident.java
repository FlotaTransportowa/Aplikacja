package database;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
public class NotifyAccident extends Notification{
    private boolean ifCollision;

    public boolean isIfCollision() {
        return ifCollision;
    }

    public void setIfCollision(boolean ifCollision) {
        this.ifCollision = ifCollision;
    }
}
