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
    @OneToOne
    @JoinColumn(name = "accountId") //pracownik zawiera referencję do konta
    private Account account;
    @OneToMany
    @JoinColumn(name = "employeeId") //telefony posiadaja identyfikatory wlasciciela - database view
    private List<Phone> phones;
    @OneToOne
    @JoinColumn(name = "addressId") //pracownik zawiera referencję do konta
    private Address address;

    @Transient
    private Button editButton = new Button("Edytuj");

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
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Button getEditButton() {
        return editButton;
    }

    public void setEditButton(Button editButton) {
        this.editButton = editButton;
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
