package models;


import javafx.collections.ObservableList;
import manager.GlobalManager;

import javax.persistence.EntityManager;

public interface BaseModel<T> {
    abstract ObservableList<T> getAll();

    /**
     * Dodaje do bazy danych obiekt przekazany przez parametr
     * @param object
     */
    public default void pushToDatabase(T object){
        EntityManager entityManager = GlobalManager.getManager();

        entityManager.getTransaction().begin();
        try {
            entityManager.persist(object);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            entityManager.getTransaction().commit();
            entityManager.refresh(object);
        }
    }
}
