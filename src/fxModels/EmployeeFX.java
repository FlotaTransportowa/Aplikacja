package fxModels;

import database.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.DriverModel;
import models.EmployeeModel;

import java.util.List;

/**
 * Model Pracownika
 */
public class EmployeeFX {
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String type;
    private String email;
    private double salary;
    private Account account;
    private List<Phone> phones;
    private Address addressOfEmployee;
    private List<Permission> permissions;

    /**
     * Pobranie wszystkich pracowników w postaci modelu EmployeeFX
     * @return Zwraca listę zgłoszeń
     */
    public static ObservableList<EmployeeFX> getAll(){
        ObservableList<EmployeeFX> employeesFX = FXCollections.observableArrayList();
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        EmployeeModel employeeModel = new EmployeeModel();

        employees = employeeModel.getAll();

        for(Employee t : employees){
            employeesFX.add(new EmployeeFX(t.getId(), t.getFirstName(), t.getLastName(), t.getAge(), t.getGender(), t.getType(), t.getEmail(), t.getSalary(), t.getAccount(), t.getPhones(), t.getAddress()));
        }

        return employeesFX;
    }

    public static ObservableList<EmployeeFX> getAllDrivers(){
        ObservableList<EmployeeFX> employeesFX = FXCollections.observableArrayList();
        ObservableList<Driver> drivers = FXCollections.observableArrayList();


        drivers = DriverModel.getAll();

        for(Driver t : drivers){
            employeesFX.add(new EmployeeFX(t.getId(), t.getFirstName(), t.getLastName(), t.getAge(), t.getGender(), t.getType(), t.getEmail(), t.getSalary(), t.getAccount(), t.getPhones(), t.getAddress(), t.getPermissions()));
        }

        return employeesFX;
    }

    public EmployeeFX(long id, String firstName, String lastName, int age, String gender, String type, String email, double salary, Account account, List<Phone> phones, Address address, List<Permission> permissions){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.email = email;
        this.salary = salary;
        this.account = account;
        this.phones = phones;
        this.addressOfEmployee = address;
        this.permissions = permissions;
    }

    public EmployeeFX(long id, String firstName, String lastName, int age, String gender, String type, String email, double salary, Account account, List<Phone> phones, Address address){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.email = email;
        this.salary = salary;
        this.account = account;
        this.phones = phones;
        this.addressOfEmployee = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Address getAddressOfEmployee() {
        return addressOfEmployee;
    }

    public void setAddressOfEmployee(Address addressOfEmployee) {
        this.addressOfEmployee = addressOfEmployee;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
