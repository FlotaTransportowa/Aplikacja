package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TitledPane;

import java.io.IOException;


public class PermissionAccordionController extends Controller {

    @FXML private TitledPane paneZlecen;
    private LoggedController loggedController;

    public PermissionAccordionController()
    {

    }
    @FXML
    private void addNewOrder() throws IOException {
        getLoggedController().addNewOrder();
    }
    @FXML private void  viewNotConfirmedTasksList()
    {
        getLoggedController().viewNotConfirmedTasksList();
    }
    @FXML private void viewTasksList() throws IOException {
        getLoggedController().viewTasksList();
    }
    @FXML private void addNewTrack() throws IOException {
        getLoggedController().addNewTrack();
    }
    @FXML private void showAllTracks()
    {
        getLoggedController().showAllTracks();
    }
    @FXML private void assignTrack()
    {
        getLoggedController().assignTrack();
    }
    @FXML private void addNewMachine() throws IOException {
        getLoggedController().addNewMachine();
    }

    @FXML private void showMachine() throws IOException {
        getLoggedController().showMachine();
    }
    @FXML private void addNewEmployee() throws IOException {
        getLoggedController().addNewEmployee();
    }
    @FXML private void showEmployee() throws IOException {
        getLoggedController().showEmployee();
    }


    public LoggedController getLoggedController() {
        return this.loggedController;
    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController=loggedController;
    }


}
