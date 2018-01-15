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
        entityManager.persist(object);
        entityManager.getTransaction().commit();
    }
}
