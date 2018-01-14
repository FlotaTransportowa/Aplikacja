package database;

import javax.persistence.*;

@Entity
public class Machine {
    @Id
    @GeneratedValue
    private long id;
    private String VIN;
    private String registrationNumber;
    private boolean busy;
    private boolean efficient;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "typeId")
    private MachineType type;

    public MachineType getType() {
        return type;
    }

    public void setType(MachineType type) {
        this.type = type;
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
}
