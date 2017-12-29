package database;

import javax.persistence.Entity;

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
