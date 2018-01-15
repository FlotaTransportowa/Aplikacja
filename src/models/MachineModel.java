package models;

import database.Machine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class MachineModel implements BaseModel<Machine>{
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
}
