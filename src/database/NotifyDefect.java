package database;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
public class NotifyDefect extends Notification{
    public NotifyDefect() {
        super();
    }

    public NotifyDefect(Date date, String description, NotifyStatus status, Machine machine, Employee employee) {
        super(date, description, status, machine, employee);
    }
}
