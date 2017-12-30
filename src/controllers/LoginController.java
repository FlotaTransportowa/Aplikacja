package controllers;

import database.Account;
import database.Employee;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import security.HashPassword;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class LoginController extends Controller {
    @FXML private TextField login;
    @FXML private TextField password;
    @FXML private ImageView imageView;
    @FXML private StackPane stackPane;

    @FXML private void check() throws IOException, SQLException, NoSuchAlgorithmException {
        String userString = login.getText();
        String passwordString = password.getText();

        Account account = Account.getAccount(userString, HashPassword.hashPassword(passwordString));

        if(account != null)
        {
            System.out.println("Zalogowano jako " + account.getLogin());
            initSystem();
            mainController.logIn(account.getEmployeeType(account));
        }
        else{
            /////////////////////////////////////////////
            // Komunikat błędu
            /////////////////////////////////////////////
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Błąd logowania");
            alert.setHeaderText("Niepoprawny login lub hasło");
            //alert.setContentText("©2017 \nDaniel Dymiński\nNorbert Gil\nMaksymilian Jagodziński\nHubert Januszek ");
            alert.showAndWait();
        }
    }

    private void initSystem() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/loggedScreen.fxml"));
        AnchorPane pane = loader.load();
        mainController.setScreen(pane);
        LoggedController loggedController = loader.getController();
        loggedController.setMainController(mainController);
        mainController.setLoggedController(loggedController);
    }
    @FXML private void checkKey() {}
    public void initialize()
    {
       /* imageView.fitWidthProperty().bind(stackPane.widthProperty());
        imageView.fitHeightProperty().bind(stackPane.heightProperty());*/
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
