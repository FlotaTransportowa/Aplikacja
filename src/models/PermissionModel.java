package models;

import database.Account;
import database.Permission;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.GlobalManager;
import validation.Pattern;
import validation.Validation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class PermissionModel implements BaseModel<Permission>{

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
