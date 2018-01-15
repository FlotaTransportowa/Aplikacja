package database;

import javax.persistence.Entity;

@Entity
public class Principal extends Dispatcher{
    public Principal(){
        super(" ", "", 0, "", "", "", 0.0);
    }
    public Principal(String firstName, String lastName, int age, String gender, String type, String email, double salary) {
        super(firstName, lastName, age, gender, type, email, salary);
    }
}
