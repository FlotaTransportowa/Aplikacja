package controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import manager.GlobalManager;

import javax.persistence.EntityManager;

public abstract class Controller{
    @FXML
    protected MainController mainController;
    private Tab thisTab;
    public final String validStyle = "-fx-background-color:#fff; -fx-border-width: 1px ; -fx-border-color: #a8a8a8; -fx-border-radius: 2px;";
    public final String nonValidStyle = "-fx-background-color:#f9a7a7; -fx-border-width: 1px ; -fx-border-color: #a8a8a8; -fx-border-radius: 2px;";

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
