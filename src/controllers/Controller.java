package controllers;

import javafx.fxml.FXML;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Iterator;
import java.util.List;

public abstract class Controller {
    @FXML
    protected MainController mainController;
    void setMainController(MainController par)
    {
        this.mainController=par;
    }

    public void create(List<Object> lista){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("myDatabase");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        entityManager.getTransaction().begin();

        for (Iterator<Object> i = lista.iterator(); i.hasNext();) {
            Object item = i.next();
            entityManager.persist(item);
        }

        entityManager.getTransaction().commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
