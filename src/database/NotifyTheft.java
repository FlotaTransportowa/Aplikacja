package database;

import javax.persistence.Entity;

@Entity
public class NotifyTheft extends Notification{
    private boolean ifVictims;

    public boolean isIfVictims() {
        return ifVictims;
    }

    public void setIfVictims(boolean ifVictims) {
        this.ifVictims = ifVictims;
    }
}
