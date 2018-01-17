package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import manager.GlobalManager;

import javax.persistence.EntityManager;

public abstract class Controller {
    @FXML
    protected MainController mainController;

    public static EntityManager entityManager = GlobalManager.getManager();

    void setMainController(MainController par)
    {
        this.mainController=par;
    }
}
