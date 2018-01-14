package database;

import javax.persistence.*;
import java.util.List;

@Entity
public class TechnicalEmployee extends Employee{
    public TechnicalEmployee(){
        super(" ", "", 0, "", "", "", 0.0);
    }

    public TechnicalEmployee(String firstName, String lastName, int age, String gender, String type, String email, double salary) {
        super(firstName, lastName, age, gender, type, email, salary);
    }
}
