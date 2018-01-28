package models;

import database.Dispatcher;
import database.Driver;
import database.Employee;
import database.Principal;
import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public interface BaseModel<T> {
    abstract ObservableList<T> getAll();
    public default void pushToDatabase(T object){
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(object);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
        }
        entityManager.refresh(object);
    }
}
