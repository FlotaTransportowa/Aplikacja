package database;

import javax.persistence.*;
import java.util.List;

@Entity
public class Address {
    @Id
    @GeneratedValue
    private long id;
    private String locality;
    private String postalCode;
    private String street;
    private String apartmentNumber;

    @OneToMany(mappedBy = "addressOfEmployee")
    private List<Employee> employees;

    @OneToMany(mappedBy = "addressOfOrder")
    private List<Order> orders;

    public Address(){}

    public Address(String locality, String postalCode, String street, String apartmentNumber) {
        this.locality = locality;
        this.postalCode = postalCode;
        this.street = street;
        this.apartmentNumber = apartmentNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }
}
