package database;

import javax.persistence.*;
import java.util.List;

@Entity
public class MachineType {
    @Id
    @GeneratedValue
    private long id;
    private String mark;
    private String model;
    private String type;
    @OneToMany(mappedBy = "typeOfMachine")
    private List<Machine> machines;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
