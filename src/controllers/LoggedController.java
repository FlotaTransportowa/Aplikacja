package controllers;

import database.Driver;
import database.Employee;
import database.Machine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.NotificationModel;
import org.controlsfx.control.SegmentedButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoggedController extends Controller {

    private List<AddEmployeeController> addEmployeeControllers = new ArrayList<>();
    private List<ShowEmployeeController> showEmployeeControllerList = new ArrayList<>();
    private List<AddMachineController> addMachineControllers = new ArrayList<>();
    private List<ShowMachineController> showMachineControllerList = new ArrayList<>();
    private List<PermissionAccordionController> permissionAccordionControllerList = new ArrayList<>();

    @FXML private TabPane tabMenu;
    @FXML private ListView lastTasks;
    @FXML private SplitPane splitPane;
    private List<String> stringList  = new ArrayList<>(5);
    private Employee loggedEmployee = null;
    private static NotificationModel notificationModel = new NotificationModel();

    @FXML private Text login;
    @FXML private Text typKonta;

    @FXML private AnchorPane welcomePanel;
    @FXML
    private Accordion accord = new Accordion();

    @FXML void closeAllTabs(){
        tabMenu.getTabs().clear();
    }

    @FXML void initialize() throws Exception
    {
        ObservableList observableList = FXCollections.observableArrayList();
        stringList.add("Przewóz materiałów");
        stringList.add("Transport sprzętu budowlanego do Kielc");
        stringList.add("Wywóz sprzętu budowlanego");
        stringList.add("Przewóz materiału");
        observableList.setAll(stringList);
        lastTasks.setItems(observableList);
    }

    @FXML void setAccountDetails(String Login, String Type) throws IOException {
        login.setText(Login);
        typKonta.setText(Type);
    }

    @FXML public void lastTasksClicked(MouseEvent arg0) {
        if(arg0.getButton().equals(MouseButton.PRIMARY)){
            if(arg0.getClickCount() == 2){
                System.out.println("clicked on " + lastTasks.getSelectionModel().getSelectedItem());
            }
        }
    }

    public void addNewTab(Node node,String title)
    {
        Tab newOrderTab = new Tab(title);
        tabMenu.getTabs().add(newOrderTab);
        newOrderTab.setContent(node);
        SingleSelectionModel<Tab> selectionModel = tabMenu.getSelectionModel();
        selectionModel.select(newOrderTab);

    }

    public void addNewTab(FXMLLoader loader,String title) throws IOException {
        Node node = loader.load();
        Tab newOrderTab = new Tab(title);
        tabMenu.getTabs().add(newOrderTab);
        newOrderTab.setContent(node);
        SingleSelectionModel<Tab> selectionModel = tabMenu.getSelectionModel();
        selectionModel.select(newOrderTab);
        Controller controller = loader.getController();
        controller.setThisTab(newOrderTab);

    }

    public void removeTab(Tab tab)
    {
        tabMenu.getTabs().remove(tab);
    }

    @FXML void addNewOrder() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addNewTask.fxml"));
            addNewTab((Node) loader.load(),"Utwórz nowe zlecenie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void addNewEmployee() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addEmployeeScreen.fxml"));
            addNewTab(loader,"Dodaj pracownika");
            AddEmployeeController addEmployeeController = loader.getController();
            addEmployeeControllers.add(addEmployeeController);
            addEmployeeController.setLoggedController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void viewNotConfirmedTasksList(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/viewNotConfirmedTasks.fxml"));
            addNewTab((Node) loader.load(),"Niezatwierdzone zlecenia");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void addNewMachine() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addNewMachineScreen.fxml"));
            addNewTab((Node) loader.load(),"Dodaj maszynę");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void showMachine() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showMachineForm.fxml"));
            addNewTab((Node) loader.load(),"Pojazdy floty");

            ShowMachineController showMachineController = loader.getController();
            showMachineControllerList.add(showMachineController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editMachine(Machine machine) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addNewMachineScreen.fxml"));
            addNewTab(loader,"Edytuj maszynę");

            AddMachineController addMachineController = loader.getController();
            addMachineControllers.add(addMachineController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void addNewTrack() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addNewTrackScreen.fxml"));
            addNewTab((Node) loader.load(),"Stwórz trasę");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editEmployee(Employee employee) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addEmployeeScreen.fxml"));
            addNewTab(loader,"Edytuj pracownika");

            AddEmployeeController addEmployeeController = loader.getController();
            addEmployeeControllers.add(addEmployeeController);
            addEmployeeController.setToEmployee(employee);
            addEmployeeController.setLoggedController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void showEmployee() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showEmployeeForm.fxml"));
            addNewTab((Node) loader.load(),"Pracownicy floty");

            ShowEmployeeController showEmployeeController = loader.getController();
            showEmployeeControllerList.add(showEmployeeController);
            showEmployeeController.setLoggedController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addEmployeePermission(Employee employee) throws IOException {
        try {
            Tab newShowEmployeeTab = new Tab("Dodawanie uprawnień");
            tabMenu.getTabs().add(newShowEmployeeTab);

            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addPermissionScreen.fxml"));
            newShowEmployeeTab.setContent((Node) loader.load());

            AddPermisionController addPermisionController = loader.getController();
            addPermisionController.setDriver((Driver) employee);
            addPermisionController.createCurrentPermissions();
            addPermisionController.createNewPermission();

            SingleSelectionModel<Tab> selectionModel = tabMenu.getSelectionModel();
            selectionModel.select(newShowEmployeeTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void viewTasksList() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showAllOrdersScreen.fxml"));
            addNewTab((Node) loader.load(), "Lista zleceń");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void assignTrack(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/assignTrackScreen.fxml"));
            addNewTab((Node) loader.load(), "Przydziel trasę");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showAllTracks(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showAllTracksScreen.fxml"));
            addNewTab((Node) loader.load(), "Lista tras");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void showDriverTracksForTake(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showAllTracksAndTakeScreen.fxml"));
            addNewTab((Node) loader.load(), "Lista tras");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void showYourTracks(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/driversTracksScreen.fxml"));
            addNewTab((Node) loader.load(), "Lista tras");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void logOut() throws IOException {
        mainController.logout();
    }

    /*@FXML void changeSizeOfTrasyTable() throws IOException {
        double value = sizeOfTrasyTable.getValue();
        sizeOfTrasyTableTextField.setText(Double.toString(value));
    }*/
    private void initDriver() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/permissionAccordionForms/driverAccordionForm.fxml"));
            accord.getPanes().clear();
            accord.getPanes().addAll(((Accordion) loader.load()).getPanes());
            PermissionAccordionController permissionAccordionController = loader.getController();
            if(permissionAccordionController !=null) {
                permissionAccordionController.setLoggedController(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initDispatcher()
    {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/permissionAccordionForms/dispatcherAccordionForm.fxml"));
            accord.getPanes().clear();
            accord.getPanes().addAll(((Accordion) loader.load()).getPanes());
            PermissionAccordionController permissionAccordionController = loader.getController();
            if(permissionAccordionController !=null) {
                permissionAccordionController.setLoggedController(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void initPrincipal()
    {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/permissionAccordionForms/principalAccordionForm.fxml"));
            accord.getPanes().clear();
            accord.getPanes().addAll(((Accordion) loader.load()).getPanes());
            PermissionAccordionController permissionAccordionController = loader.getController();
            if(permissionAccordionController !=null) {
                permissionAccordionController.setLoggedController(this);
                permissionAccordionControllerList.add(permissionAccordionController);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void initPermissions() throws IOException {
        switch (typKonta.getText()){
            case "Kierownik":
                initPrincipal();
                break;
            case "Dyspozytor":
                initDispatcher();
                break;
            case "Kierowca":
                initDriver();
                break;
        }
    }



    public AnchorPane getWelcomePanel() {
        return welcomePanel;
    }

    public void showYoursTracks(Employee employee) {

    }

    public Employee getLoggedEmployee() {
        return loggedEmployee;
    }

    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = loggedEmployee;
    }

    public void showAllNotifications() {


    }
}
