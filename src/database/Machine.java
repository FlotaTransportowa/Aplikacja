package database;

import javafx.scene.control.ButtonBar;
import models.MachineTypeModel;

import javax.persistence.*;
import java.util.List;

@Entity
public class Machine {
    @Id
    @GeneratedValue
    private long id;
    private String VIN;
    private String registrationNumber;
    private boolean busy;
    private boolean efficient;
    @OneToMany(mappedBy = "machineOfTrack")
    private List<Track> tracks;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "typeID", referencedColumnName = "id")
    private MachineType typeOfMachine;

    @Transient
    private ButtonBar buttonBar = new ButtonBar();

    public MachineType getType() {
        return typeOfMachine;
    }

    public void setType(MachineType type) {
        this.typeOfMachine = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public boolean isEfficient() {
        return efficient;
    }

    public void setEfficient(boolean efficient) {
        this.efficient = efficient;
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public ButtonBar getButtonBar() {
        return buttonBar;
    }

    @Override
    public String toString() {
        return typeOfMachine +": "+ registrationNumber;
    }
}
