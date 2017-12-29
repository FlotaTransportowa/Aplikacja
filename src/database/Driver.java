package database;

import javax.persistence.*;
import java.util.List;

@Entity
public class Driver extends Employee {
    @OneToMany
    @JoinColumn(name = "employeeId")
    private List<Permission> permissions;
    private boolean busy;

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
