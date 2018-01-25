package models;

import database.Address;
import database.Machine;
import database.MachineType;
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

    public static Machine getMachine(MachineType type, String registrationNum, String VINNum){
        Machine machine = new Machine();
        machine.setType(type);
        machine.setBusy(false);
        machine.setEfficient(true);
        machine.setRegistrationNumber(registrationNum);
        machine.setVIN(VINNum);
        return machine;
    }

    public MachineType retExistType(MachineType type){
        MachineType existType = null;
        EntityManager entityManager = GlobalManager.getManager();

        try {
            entityManager.getTransaction().begin();
            TypedQuery<MachineType> query = entityManager.createQuery("select a from MachineType a where mark = :mark and model = :model and type = :type", MachineType.class);
            query.setParameter("mark", type.getMark());
            query.setParameter("model", type.getModel());
            query.setParameter("type", type.getType());
            query.setMaxResults(1);
            existType = query.getSingleResult();
        } catch (Exception e){
            System.out.println("Typ maszyny nie istnieje i został utworzony.");
        } finally {
            entityManager.getTransaction().commit();
            return existType;
        }

    }
}
