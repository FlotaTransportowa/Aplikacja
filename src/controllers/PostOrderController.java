package controllers;

import javafx.fxml.FXML;
import database.Order;

public class PostOrderController extends Controller{
    private LoggedController loggedController;
    Order order = null;

    @FXML
    void initialize(){

    }

    public void setOrder(Order o){
        order = o;
    }

    public LoggedController getLoggedController() {
        return this.loggedController;
    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController=loggedController;
    }
}
