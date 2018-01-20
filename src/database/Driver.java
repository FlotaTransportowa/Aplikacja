package database;

import javax.persistence.*;
import java.util.List;

@Entity
public class Driver extends Employee {
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Permission> permissions;
    @OneToMany(mappedBy = "driverOfTrack")
    private List<Track> tracks;

    public Driver(){
        super(" ", "", 0, "", "", "", 0.0);
    }

    public Driver(String firstName, String lastName, int age, String gender, String type, String email, double salary) {
        super(firstName, lastName, age, gender, type, email, salary);
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }
}
