package controllers;

import database.Account;
import database.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import models.AccountModel;
import security.HashPassword;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.awt.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginController extends Controller {
    @FXML private TextField login;
    @FXML private TextField password;
    @FXML private ImageView imageView;
    @FXML private StackPane stackPane;
    private Account account = null;

    private static AnchorPane welcomePanel;
    private LoggedController loggedController;


    @FXML private void check() throws IOException, SQLException, NoSuchAlgorithmException {
        account = AccountModel.getAccount(login.getText(), HashPassword.hashPassword(password.getText()));

        if(account != null)
        {
            initSystem(account.getLogin());
            mainController.logIn();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Błąd logowania");
            alert.setHeaderText("Niepoprawny login lub hasło");
            alert.showAndWait();
        }
    }

    private void initSystem(String Login) throws IOException {
        if(account == null)
            return;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/loggedScreen.fxml"));
        AnchorPane pane = loader.load();
        mainController.setScreen(pane);
        loggedController = loader.getController();
        loggedController.setMainController(mainController);
        mainController.setLoggedController(loggedController);
        mainController.setAccountLogin(Login);
        loggedController.setAccountDetails(account.getLogin(), AccountModel.getEmployeeTypePL(account));
    }
    @FXML private void checkKey() {}
    public void initialize()
    {
        login.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    check();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        password.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                try {
                    check();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @FXML void clearForm()
    {
        login.setText("");
        password.setText("");
    }
}
