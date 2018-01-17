package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import manager.GlobalManager;

import javax.persistence.EntityManager;

public abstract class Controller {
    @FXML
    protected MainController mainController;
    private Tab thisTab;

    public static EntityManager entityManager = GlobalManager.getManager();

    void setMainController(MainController par)
    {
        this.mainController=par;
    }

    public Tab getThisTab() {
        return thisTab;
    }

    public void setThisTab(Tab thisTab) {
        this.thisTab = thisTab;
    }
}
