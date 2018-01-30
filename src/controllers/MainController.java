package controllers;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

//import javafx.event.EventHandler;

/**
 * Kontroler inicjujący okno aplikacji
 */
public class MainController extends Controller{

    private String AccountType = "";
    private String AccountLogin = "";

    @FXML
        private LoggedController loggedController;
    @FXML
        private AnchorPane mainPane;
    @FXML
        private ReadOnlyDoubleProperty stageWidthProperty;
    @FXML
        private MenuBar topMenuBar;
    @FXML
        private Menu systemMenu;
    @FXML
    public  void initialize() throws Exception{
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/loginScreen.fxml"));
        StackPane pane = loader.load();
        setScreen(pane);
        LoginController loginController = loader.getController();
        loginController.setMainController(this);
    }


    void setAccountLogin(String Login) { AccountLogin = Login; }
    void setAccountType(String Type) { AccountType = Type; }
    String getAccountLogin() { return AccountLogin; }
    String getAccountType() { return AccountType; }

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

    /**
     * Zalogowanie
     */
    void logIn()
    {
        topMenuBar.setVisible(true);
        mainPane.setLayoutY(26);
    }

    /**
     * Wylogowanie
     */
    @FXML void logout(){
        try {
            topMenuBar.setVisible(false);
            mainPane.setLayoutY(0);
            AccountLogin = "";
            AccountType = "";
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Wyjście z aplikacji
     */
    @FXML void exitApp(){
        System.exit(0);
        //Platform.exit();
    }

    @FXML void setLoggedController(LoggedController loggedController) throws IOException {
        this.loggedController = loggedController;
    }

    @FXML void closeAllTabs() throws  Exception{
        loggedController.closeAllTabs();
    }

    /**
     * Informację o twórcach
     */
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

    @FXML void welcomePanelMenuItemOpen()
    {
        loggedController.addNewTab(loggedController.getWelcomePanel(),"Karta powitalna");
    }
}
