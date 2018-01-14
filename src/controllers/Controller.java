package controllers;

import javafx.fxml.FXML;
import manager.GlobalManager;

import javax.persistence.EntityManager;
import java.util.Iterator;
import java.util.List;

public abstract class Controller {
    @FXML
    protected MainController mainController;
    public static EntityManager entityManager = GlobalManager.getManager();

    void setMainController(MainController par)
    {
        this.mainController=par;
    }

    public void create(List<Object> lista){
        entityManager.getTransaction().begin();

        for (Iterator<Object> i = lista.iterator(); i.hasNext();) {
            Object item = i.next();
            entityManager.persist(item);
        }

        entityManager.getTransaction().commit();

    }
}
