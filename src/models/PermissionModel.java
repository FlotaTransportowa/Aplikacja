package models;

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

}
