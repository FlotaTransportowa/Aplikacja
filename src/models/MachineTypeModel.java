package models;

import database.Machine;
import database.MachineType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MachineTypeModel implements BaseModel<MachineType>{

    @Override
    public ObservableList<MachineType> getAll() {
        ObservableList<MachineType> machineTypes = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        try {
            entityManager.getTransaction().begin();
            TypedQuery<MachineType> query = entityManager.createQuery("select t from MachineType t", MachineType.class);
            List<MachineType> machineTypes1 = query.getResultList();
            machineTypes.addAll(machineTypes1);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }

        return machineTypes;
    }

    public static MachineType getModel(String typeOfMachine) {

        EntityManager entityManager = GlobalManager.getManager();
        MachineType machineType = null;
        try {
            entityManager.getTransaction().begin();
            TypedQuery<MachineType> query = entityManager.createQuery("select e from MachineType e where id = :typeId", MachineType.class);
            query.setParameter(1,typeOfMachine);
            machineType = query.getSingleResult();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        finally {
            entityManager.getTransaction().commit();
        }
        return machineType;
    }
}
