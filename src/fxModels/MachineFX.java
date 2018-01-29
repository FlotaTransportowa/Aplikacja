package fxModels;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.MachineModel;

import java.util.List;

public class MachineFX {
    private String mark;
    private String model;
    private String type;
    private long machineID;
    private String registrationNumber;
    private String VIN;

    public static ObservableList<MachineFX> getAllAvailable(){
        ObservableList<MachineFX> machinesFX = FXCollections.observableArrayList();
        ObservableList<Machine> machines = FXCollections.observableArrayList();
        MachineModel machineModel = new MachineModel();

        machines = machineModel.getAllAvailable();

        for(Machine t : machines){
            machinesFX.add(new MachineFX(t.getType().getMark(), t.getType().getModel(), t.getType().getType(), t.getId(), t.getRegistrationNumber(), t.getVIN()));
        }

        return machinesFX;
    }

    public MachineFX(String mark, String model, String type, long machineID, String registrationNumber, String VIN){
        this.mark = mark;
        this.model = model;
        this.type = type;
        this.machineID = machineID;
        this.registrationNumber = registrationNumber;
        this.VIN = VIN;
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

    public long getMachineID() {
        return machineID;
    }

    public void setMachineID(long machineID) {
        this.machineID = machineID;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getVIN() {
        return VIN;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }
}
