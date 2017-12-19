package controllers;

import database.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import security.HashPassword;

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

        if(Account.getAccount(userString, HashPassword.hashPassword(passwordString)))
        {
            System.out.println("Zalogowano");
            initSystem();
            mainController.logIn("Kierownik");
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
