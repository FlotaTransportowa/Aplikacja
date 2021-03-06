package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import org.controlsfx.control.SegmentedButton;

import java.io.IOException;

import static controllers.AddNotificationController.NotifyType.ACCIDENT;
import static controllers.AddNotificationController.NotifyType.DEFECT;
import static controllers.AddNotificationController.NotifyType.THEFT;


/**
 * Kontroler zarządzający uprawnieniami użytkownika
 */
public class PermissionAccordionController extends Controller {

    @FXML private TitledPane paneZlecen;
    private LoggedController loggedController;
    @FXML private ToggleButton zgloszenieToggleButton;
    @FXML private ToggleButton zgloszenieToggleButton2;
    @FXML private ToggleButton zgloszenieToggleButton3;
    @FXML private SegmentedButton zgloszenieSegmentButton;
    @FXML private TextField sizeOfTrasyTableTextField,  sizeOfTrasyTableTextField1,  sizeOfTrasyTableTextField2;
    @FXML private Slider sizeOfTrasyTable, sizeOfTrasyTable1,sizeOfTrasyTable2;

    private String title = new String();
    private AddNotificationController.NotifyType notifyType;

    public PermissionAccordionController()
    {

    }
    public void initialize()
    {
        if(sizeOfTrasyTable!=null && sizeOfTrasyTableTextField!=null )
            configSlider(sizeOfTrasyTable,sizeOfTrasyTableTextField);
        if(sizeOfTrasyTable1!=null && sizeOfTrasyTableTextField1!=null )
            configSlider(sizeOfTrasyTable1,sizeOfTrasyTableTextField1);
        if(sizeOfTrasyTable2!=null && sizeOfTrasyTableTextField2!=null )
            configSlider(sizeOfTrasyTable2,sizeOfTrasyTableTextField2);
        if(zgloszenieSegmentButton!=null&&zgloszenieToggleButton!=null&&zgloszenieToggleButton2!=null&&zgloszenieToggleButton3!=null)
            zgloszenieSegmentButton.getButtons().addAll(zgloszenieToggleButton, zgloszenieToggleButton2, zgloszenieToggleButton3);
    }
    private void configSlider(Slider slider, TextField textField)
    {
        slider.setMin(1);
        slider.setMax(50);

        slider.setValue(Integer.parseInt(textField.getText()));

        slider.setMajorTickUnit(10);
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(5);
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                textField.setText(Integer.toString(newValue.intValue()));

            }
        });
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

    @FXML private void viewYoursTasksList() throws IOException {
        getLoggedController().viewYoursTasksList();
    }

    @FXML private void addNewTrack() throws IOException {
        getLoggedController().addNewTrack();
    }

    @FXML private void showAllTracks()
    {
        getLoggedController().showAllTracks();
    }

    @FXML private void showDriverTracksForTake(){
        getLoggedController().showDriverTracksForTake();
    }

    @FXML private void showYoursTracks(){
        getLoggedController().showYoursTracks();
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

    @FXML private void showOrderReports() throws IOException{
        getLoggedController().showOrderReports();
    }

    /**
     * Sprawdzenie czy został wybrany typ zgłoszenia oraz ustawienie odpowiednich pól na danych typ
     * @return Prawdę gdy wybrano jakiś typ, fałsz w przeciwnym przypadku
     */
    private boolean findSelectedType() {
        if(zgloszenieToggleButton.isSelected()){
            title = "Zgłoszenie usterki";
            notifyType = DEFECT;
        }
        else if(zgloszenieToggleButton2.isSelected()){
            title = "Zgłoszenie kradzieży";
            notifyType = THEFT;
        }
        else if (zgloszenieToggleButton3.isSelected()) {
            title = "Zgłoszenie wypadku";
            notifyType = ACCIDENT;
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Błąd tworzenia zgłoszenia");
            alert.setHeaderText("Nie wybrano typu zgłoszenia");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    /**
     * Dodanie nowej karty do dodania zgłoszenia
     */
    @FXML private void addNewNotify() {
        if(findSelectedType()) {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/Notification/notificationScreen.fxml"));
            try {
                loggedController.addNewTab(loader, title);
                AddNotificationController addNotificationController = loader.getController();
                addNotificationController.setPermissionAccordionController(this);
                addNotificationController.setNotifyType(notifyType);
                addNotificationController.initNofity();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Dodanie nowerj karty wyświetlające wszystkie zgłoszenia wybranego typu dla notifyType
     */
    @FXML
    private void showAllNotifications() {
        if(findSelectedType()) {
            ShowAllNotificationsController showAllNotificationsController = null;
            try {
                FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showAllNotificationScreen.fxml"));
                loggedController.addNewTab(loader, "Lista zgłoszeń");
                showAllNotificationsController = loader.getController();
                showAllNotificationsController.setPermissionAccordionController(this);
                showAllNotificationsController.setNotifyType(notifyType);
                showAllNotificationsController.initNofity();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public LoggedController getLoggedController() {
        return this.loggedController;
    }

    public void setLoggedController(LoggedController loggedController) {
        this.loggedController=loggedController;
    }


}
