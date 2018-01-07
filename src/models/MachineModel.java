package models;

import database.Machine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class MachineModel {

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
}
