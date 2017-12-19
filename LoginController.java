package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML private TextField login;
    @FXML private TextField password;
    @FXML private void check() {
        String userString = login.getText();
        String passwordString = password.getText();
    }
}
