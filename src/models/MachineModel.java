package models;

import database.Employee;
import database.Machine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class MachineModel implements BaseModel<Machine>{

    public ObservableList<Machine> getAllMachines(){
        ObservableList<Machine> machines = FXCollections.observableArrayList();
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();
        TypedQuery<Machine> query = entityManager.createQuery("select m from Machine m", Machine.class);
        List<Machine> machines1 = query.getResultList();
        entityManager.getTransaction().commit();

        machines.addAll(machines1);

        entityManager.close();
        entityManagerFactory.close();
        return machines;
    }

    @Override
    public ObservableList<Machine> getAll() {
        ObservableList<Machine> machines = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        TypedQuery<Machine> query = entityManager.createQuery("select e from Machine e", Machine.class);
        List<Machine> machines1 = query.getResultList();
        entityManager.getTransaction().commit();

        machines.addAll(machines1);
        return machines;
    }

    @Override
    public boolean valid(ArrayList<String> lista) {
        return false;
    }
}
