package models;

import database.MachineType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Model typu maszyny
 */
public class MachineTypeModel implements BaseModel<MachineType>{

    /**
     * Szuka wszystkich typów maszyn
     * @return Zwaraca listę typów maszyn
     */
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
}
