package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NewTaskController extends  Controller {

    @FXML
    private TextField imie;
    @FXML
    private TextField nazwisko;

    @FXML void initialize() throws Exception {
        imie.setText("coś tam");

    }
}
