package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.HBox;

public class editEmployeeController {
    @FXML
    private ChoiceBox phoneTypeChoiseBox,phoneTypeChoiseBox1,phoneTypeChoiseBox2;

    @FXML private HBox phoneHBox1;
    @FXML private HBox phoneHBox2;
    @FXML private HBox phoneHBox3;

    @FXML
    void initialize(){
        phoneTypeChoiseBox.setItems(FXCollections.observableArrayList(
                "Domowy", "Służbowy", "Komórkowy")
        );
        phoneTypeChoiseBox1 .setItems(FXCollections.observableArrayList(
                "Domowy", "Służbowy", "Komórkowy")
        );
        phoneTypeChoiseBox2.setItems(FXCollections.observableArrayList(
                "Domowy", "Służbowy", "Komórkowy")
        );
        phoneTypeChoiseBox.getSelectionModel().selectFirst();
        phoneTypeChoiseBox1.getSelectionModel().selectFirst();
        phoneTypeChoiseBox2.getSelectionModel().selectFirst();
    }

    @FXML
    private void addAction(){
        if(!phoneHBox2.isVisible()){
            phoneHBox2.setVisible(true);
            phoneHBox2.setDisable(false);
        }
        else if(!phoneHBox3.isVisible()){
            phoneHBox3.setVisible(true);
            phoneHBox3.setDisable(false);
        }
    }
    @FXML private  void remove2ndPhoneBox(){
        phoneHBox2.setVisible(false);
        phoneHBox2.setDisable(true);
    }
    @FXML private  void remove3rdPhoneBox(){
        phoneHBox3.setVisible(false);
        phoneHBox3.setDisable(true);
    }
}
