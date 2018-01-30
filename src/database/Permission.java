package database;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Permissions")
public class Permission {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String description;
    @ManyToMany(mappedBy = "permissions")
    private List<Driver> drivers;

    public Permission(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @Override
    public String toString() {
        return getName();
    }
}
