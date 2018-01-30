package fxModels;

import database.Driver;
import database.Employee;
import database.Permission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.DriverModel;
import models.EmployeeModel;

import java.util.List;

/**
 * Model Kierowcy z uprawnieniami
 */
public class DriverWithPermsFX {
    private long employeeID;
    private String firstName;
    private String lastName;
    private String type;
    private int age;
    private List<Permission> perms;

    /**
     * Pobranie wszystkich kierowców z uprawnieniami
     * @return Zwraca listę zgłoszeń jako model DriverWithPermsFX
     */
    public static ObservableList<DriverWithPermsFX> getAll(){
        ObservableList<DriverWithPermsFX> driversWithPermsFX = FXCollections.observableArrayList();
        ObservableList<Driver> drivers = FXCollections.observableArrayList();
        EmployeeModel employeeModel = new EmployeeModel();

        drivers = DriverModel.getAll();

        for(Driver t : drivers){
            driversWithPermsFX.add(new DriverWithPermsFX(t.getId(), t.getFirstName(), t.getLastName(), t.getType(), t.getAge(), t.getPermissions()));
        }

        return driversWithPermsFX;
    }

    public DriverWithPermsFX(long employeeID, String firstName, String lastName, String type, int age, List<Permission> perms){
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.age = age;
        this.perms = perms;
    }

    public long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(long employeeID) {
        this.employeeID = employeeID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Permission> getPerms() {
        return perms;
    }

    public void setPerms(List<Permission> perms) {
        this.perms = perms;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
