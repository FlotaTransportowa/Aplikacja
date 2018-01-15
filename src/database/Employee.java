package database;

import javafx.event.EventHandler;
import javafx.scene.control.Button;

import javax.persistence.*;

import javafx.event.ActionEvent;
import java.sql.SQLException;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeOfEmployee")
public abstract class Employee {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
    private String type;
    private String email;
    private double salary;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "accountId")
    private Account account;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "employeeId")
    private List<Phone> phones;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "addressID", referencedColumnName = "id")
    private Address addressOfEmployee;

    public Employee(String firstName, String lastName, int age, String gender, String type, String email, double salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.type = type;
        this.email = email;
        this.salary = salary;
    }

    @Transient
    private Button editButton = new Button("Edytuj");
    @Transient
    private Button deleteButton = new Button("Usuń");

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public Address getAddress() {
        return addressOfEmployee;
    }

    public void setAddress(Address address) {
        this.addressOfEmployee = address;
    }

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
    }

    public Button getDeleteButton() {
        return deleteButton;
    }

    public void setDeleteButton(Button deleteButton) {
        this.deleteButton = deleteButton;
    }

/*    public EventHandler<ActionEvent> editAction(ActionEvent event){

        System.out.println(this.getId());
    }*/

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
