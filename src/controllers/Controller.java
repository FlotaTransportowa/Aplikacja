package controllers;

import javafx.fxml.FXML;

public abstract class Controller {
    @FXML
    protected MainController mainController;
    void setMainController(MainController par)
    {
        this.mainController=par;
    }
}
