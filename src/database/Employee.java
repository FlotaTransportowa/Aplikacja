package database;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "typeOfEmployee")
public class Employee {
    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String gender;
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

    private static EntityManager entityManager;
    public static List<Employee> findAllEmployees() {
            EntityManagerFactory factory = Persistence
                    .createEntityManagerFactory("myDatabase");
            entityManager = factory.createEntityManager();
            entityManager.getTransaction().begin();
            List<Employee> listPersons = entityManager.createQuery(
                    "SELECT p FROM Employee p").getResultList();
            entityManager.getTransaction().commit();
            entityManager.close();
            factory.close();
            /*if (listPersons == null) {
                System.out.println("No persons found . ");
            } else {
                for (Employee person : listPersons) {
                    System.out.print("Person name= " + person.getFirstName()
                            + ", gender" + person.getGender() + ", birthday="
                            + person.getLastName());
                }
            }*/
            return listPersons;
        }


}
