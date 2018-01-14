package database;

import javax.persistence.Entity;

@Entity
public class Dispatcher extends Driver{
    public Dispatcher(){
        super(" ", "", 0, "", "", "", 0.0);
    }

    public Dispatcher(String firstName, String lastName, int age, String gender, String type, String email, double salary) {
        super(firstName, lastName, age, gender, type, email, salary);
    }
}
