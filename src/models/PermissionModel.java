package models;

import database.Driver;
import database.Permission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Model uprawnień
 */
public class PermissionModel implements BaseModel<Permission>{

    /**
     * Szuka wszystkich uprawnień
     * @return Zwraca listę uprawnień
     */
    @Override
    public ObservableList getAll() {
        ObservableList<Permission> perms = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        TypedQuery<Permission> query = entityManager.createQuery("select e from Permission e", Permission.class);
        List<Permission> perms1 = query.getResultList();
        entityManager.getTransaction().commit();

        perms.addAll(perms1);
        return perms;
    }

    public ObservableList getAllWhere(Driver driver) {
        ObservableList<Permission> perms = FXCollections.observableArrayList();
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        TypedQuery<Permission> query = entityManager.createQuery("select e from Permission e where drivers_id : driverId", Permission.class);
        query.setParameter("driverId", driver);
        List<Permission> perms1 = query.getResultList();
        entityManager.getTransaction().commit();

        perms.addAll(perms1);
        return perms;
    }

    public Permission getPermision(String perm)
    {
        EntityManager entityManager = GlobalManager.getManager();
        Permission permission = null;
        try {

            entityManager.getTransaction().begin();
            TypedQuery<Permission> query = entityManager.createQuery("select a from Permision a where a.name = :perm ", Permission.class);
            query.setParameter("name", perm);
            permission = query.getSingleResult();
            entityManager.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return permission;
    }
}
