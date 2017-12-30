package controllers;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

//import javafx.event.EventHandler;

public class MainController {

    @FXML
        private LoggedController loggedController;
    @FXML
        private AnchorPane mainPane;
    @FXML
        private ReadOnlyDoubleProperty stageWidthProperty;
    @FXML
        private MenuBar topMenuBar;
    @FXML
    public  void initialize() throws Exception{
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/loginScreen.fxml"));
        StackPane pane = loader.load();
        setScreen(pane);
        LoginController loginController = loader.getController();
        loginController.setMainController(this);
    }

    void setScreen(StackPane pane) {

        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }
    void setScreen(AnchorPane pane) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }
    void setScreen(Pane pane) {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(pane);
    }

    void logIn(String Type)
    {
        topMenuBar.setVisible(true);
        mainPane.setLayoutY(26);
        if(Type.equals("kierownik"))
        {

        }
    }

    @FXML void logout(){
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML void exitApp(){

        Platform.exit();
    }

    @FXML void setLoggedController(LoggedController loggedController) throws IOException {
        this.loggedController = loggedController;
    }

    @FXML void closeAllTabs() throws  Exception{
        loggedController.closeAllTabs();
    }

    @FXML
    void about()
    {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O programie");
        alert.setHeaderText("Zarządzanie flotą samochodów w firmie budowlanej\nwersja 0.1");
        alert.setContentText("©2017 \nDaniel Dymiński\nNorbert Gil\nMaksymilian Jagodziński\nHubert Januszek ");

        alert.showAndWait();
    }

    public void setStageWidthProperty(ReadOnlyDoubleProperty a) {
        this.stageWidthProperty = a;
    }
    public ReadOnlyDoubleProperty getStageWidthProperty() {
        return this.stageWidthProperty;
    }

}
