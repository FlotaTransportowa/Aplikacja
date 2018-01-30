package controllers;

import database.Driver;
import database.Employee;
import database.Order;
import database.Machine;
import fxModels.OrderFX;
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
import models.EmployeeModel;
import models.NotificationModel;
import models.TrackModel;
import org.controlsfx.control.SegmentedButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Kontroler obsługujący zalogowanego użytkownika
 */
public class LoggedController extends Controller {

    private List<AddEmployeeController> addEmployeeControllers = new ArrayList<>();
    private List<ShowAllEmployeesController> showEmployeeControllerList = new ArrayList<>();
    private List<AddMachineController> addMachineControllers = new ArrayList<>();
    private List<ShowAllOrdersController> showAllOrdersControllerList = new ArrayList<>();
    private List<ShowOrderReportsController> showOrderReportsList = new ArrayList<>();
    private List<PostOrderController> postOrderControllerList = new ArrayList<>();
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
        ObservableList<OrderFX> dataOders = FXCollections.observableArrayList(OrderFX.getEmployeeOrders(EmployeeModel.getEmployee(account)));
        ObservableList observableList = FXCollections.observableArrayList();

        lastTasks.setPlaceholder(new Label("Obecenie nie przydzielono Ci żadnych zleceń."));

        for(OrderFX o : dataOders)
            stringList.add(o.getTitle());

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

    /**
     * Dodanie nowej karty
     * @param node Zawartośc do dodania
     * @param title Tytuł karty
     */
    public void addNewTab(Node node,String title)
    {
        Tab newOrderTab = new Tab(title);
        tabMenu.getTabs().add(newOrderTab);
        newOrderTab.setContent(node);
        SingleSelectionModel<Tab> selectionModel = tabMenu.getSelectionModel();
        selectionModel.select(newOrderTab);

    }
    /**
     * Dodanie nowej karty
     * @param loader FXMLLoader z wczytanym plikiem fxml
     * @param title Tytuł karty
     */
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

    /**
     * Usuwa kartę wskazaną w tab
     * @param tab Karta do usunięcia
     */
    public void removeTab(Tab tab)
    {
        tabMenu.getTabs().remove(tab);
    }

    /**
     * Dodanie nowej karty z dodaniem zlecenia
     */
    @FXML void addNewOrder()  {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addNewTask.fxml"));
            addNewTab(loader,"Utwórz nowe zlecenie");
            AddNewTaskController addNewTaskController = loader.getController();
            addNewTaskController.setLoggedController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z dodaniem pracownika
     */
    @FXML void addNewEmployee() {
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

    /**
     * Dodanie nowej karty z widokiem niezatwierdzonych zleceń
     */
    @FXML
    void viewNotConfirmedTasksList(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/viewNotConfirmedTasks.fxml"));
            addNewTab(loader,"Niezatwierdzone zlecenia");
            ViewNotConfirmedTasksController controller = loader.getController();
            controller.setLoggedController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z dodaniem maszyny
     */
    @FXML void addNewMachine()  {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addNewMachineScreen.fxml"));
            addNewTab(loader,"Dodaj maszynę");
            AddMachineController controller = loader.getController();
            controller.setLoggedController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z widokiem maszyn
     */
    @FXML void showMachine()  {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showMachineForm.fxml"));
            addNewTab((Node) loader.load(),"Pojazdy floty");

            ShowMachineController showMachineController = loader.getController();
            showMachineControllerList.add(showMachineController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z edycją maszyn
     * @param machine MAszyna do edycji
     */
    public void editMachine(Machine machine)  {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addNewMachineScreen.fxml"));
            addNewTab(loader,"Edytuj maszynę");

            AddMachineController addMachineController = loader.getController();
            addMachineControllers.add(addMachineController);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z dodaniem trasy
     */
    @FXML void addNewTrack() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addNewTrackScreen.fxml"));
            addNewTab(loader,"Stwórz trasę");
            AddNewTrackController controller = loader.getController();
            controller.setLoggedController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z edycją pracownika
     * @param employee Pracownik do edycji
     */
    public void editEmployee(Employee employee) {
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
    /**
     * Dodanie nowej karty z widokiem pracowników
     */
    @FXML void showEmployee() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showAllEmployeesScreen.fxml"));
            addNewTab((Node) loader.load(),"Pracownicy floty");

            ShowAllEmployeesController showEmployeeController = loader.getController();
            showEmployeeControllerList.add(showEmployeeController);
            showEmployeeController.setLoggedController(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z księgowaniem zleceń
     * @param  order Zlecenie do zaksięgowania
     */
    public void toPostOrder(Order order) {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/postOrderScreen.fxml"));
            addNewTab(loader,"Zaksięguj zlecenie");

            PostOrderController postOrderController = loader.getController();
            postOrderControllerList.add(postOrderController);
            postOrderController.setOrder(order);
            postOrderController.setLoggedController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z raportem zleceń
     */
    public void showOrderReports(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showOrderReportsScreen.fxml"));
            addNewTab(loader,"Raporty zleceń");

            ShowOrderReportsController showOrderReportsController = loader.getController();
            showOrderReportsList.add(showOrderReportsController);
            showOrderReportsController.setLoggedController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z dodaniem uprawnień dla pracownika przekazanego przez paramter
     * @param employee Pracownik do dodania uprawnienia
     */
    void addEmployeePermission(Employee employee)  {
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
    /**
     * Dodanie nowej karty z widokiem listy zleceń
     */
    @FXML void viewTasksList() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showAllOrdersScreen.fxml"));
            addNewTab(loader, "Lista zleceń ");
            ShowAllOrdersController showAllOrdersController = loader.getController();
            showAllOrdersController.setLoggedController(this);
            showAllOrdersController.initAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z widokiem zleceń zalogowanego pracownika
     */
    @FXML void viewYoursTasksList() {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showAllOrdersScreen.fxml"));
            addNewTab(loader, loggedEmployee+": Lista zleceń ");
            ShowAllOrdersController showAllOrdersController = loader.getController();
            showAllOrdersController.setLoggedController(this);
            showAllOrdersController.initYours();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Dodanie nowej karty z widokiem przydzielenia trasy
     */
    @FXML
    void assignTrack(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/assignTrackScreen.fxml"));
            addNewTab((Node) loader.load(), "Przydziel trasę");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dodanie nowej karty z widokiem listy tras
     */
    @FXML
    void showAllTracks(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/driversTracksScreen.fxml"));
            addNewTab(loader, "Lista tras");
            DriversTracksController controller = loader.getController();
            controller.setLoggedController(this);
            controller.initAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dodanie nowej karty z widokiem tras do wzięcia
     */
    @FXML
    void showDriverTracksForTake(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/showAllTracksAndTakeScreen.fxml"));
            addNewTab((Node) loader.load(), "Lista tras");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dodanie nowej karty z lista tras zalogowanego pracownika
     */
    @FXML
    void showYoursTracks(){
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/driversTracksScreen.fxml"));
            addNewTab(loader, loggedEmployee+": Lista tras");
            DriversTracksController controller = loader.getController();
            controller.setLoggedController(this);
            controller.initYours();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda do wylogowania
     */
    @FXML void logOut()  {
        mainController.logout();
    }

    /*@FXML void changeSizeOfTrasyTable() throws IOException {
        double value = sizeOfTrasyTable.getValue();
        sizeOfTrasyTableTextField.setText(Double.toString(value));
    }*/

    /**
     * Metoda ustawiająca widok dla uprawnień kierowcy
     */
    private void initDriver() {
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
    /**
     * Metoda ustawiająca widok dla uprawnień dyspozytora
     */
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
    /**
     * Metoda ustawiająca widok dla uprawnień kierownika
     */
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
    /**
     * Metoda ustawiająca uprawnienia w zależności od typu zalogowanego konta
     * @throws IOException Wyjątek
     */
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



    public Employee getLoggedEmployee() {
        return loggedEmployee;
    }

    public void setLoggedEmployee(Employee loggedEmployee) {
        this.loggedEmployee = loggedEmployee;
    }

    public void showAllNotifications() {
    }
}