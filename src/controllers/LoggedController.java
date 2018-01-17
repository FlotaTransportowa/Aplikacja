package controllers;

import database.Driver;
import database.Employee;
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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.controlsfx.control.SegmentedButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoggedController extends Controller {

    private List<AddEmployeeController> addEmployeeControllers = new ArrayList<>();
    private List<ShowEmployeeController> showEmployeeControllerList = new ArrayList<>();

    @FXML private TabPane tabMenu;
    @FXML private ListView lastTasks;
    @FXML private SplitPane splitPane;
    private List<String> stringList  = new ArrayList<>(5);
    @FXML private Slider sizeOfTrasyTable;
    @FXML private TextField sizeOfTrasyTableTextField;
    @FXML private ToggleButton zgloszenieToggleButton;
    @FXML private ToggleButton zgloszenieToggleButton2;
    @FXML private ToggleButton zgloszenieToggleButton3;
    @FXML private SegmentedButton zgloszenieSegmentButton;
    @FXML private Text login;
    @FXML private Text typKonta;

    @FXML private AnchorPane welcomePanel;
    @FXML
    private Accordion accord;
    @FXML
    private TitledPane pane1;

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
        accord.setExpandedPane(pane1);

        sizeOfTrasyTable.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                sizeOfTrasyTableTextField.setText(Integer.toString(newValue.intValue()));
            }
        });
        zgloszenieSegmentButton.getButtons().addAll(zgloszenieToggleButton, zgloszenieToggleButton2, zgloszenieToggleButton3);
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

    @FXML void addNewOrder() throws IOException {
        try {
            addNewTab((Node) FXMLLoader.load(this.getClass().getResource("/fxml/addNewTask.fxml")),"Utwórz nowe zlecenie");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void addNewEmployee() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addEmployeeScreen.fxml"));
            addNewTab((Node) loader.load(),"Dodaj pracownika");

            addEmployeeControllers.add(loader.getController());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void editEmployee(Employee employee) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addEmployeeScreen.fxml"));
            addNewTab((Node) loader.load(),"Edytuj pracownika");

            AddEmployeeController addEmployeeController = loader.getController();
            addEmployeeControllers.add(addEmployeeController);
            addEmployeeController.setToEmployee(employee);

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


            /*
            Tab newShowEmployeeTab = new Tab("Pracownicy floty");
            tabMenu.getTabs().add(newShowEmployeeTab);


            newShowEmployeeTab.setContent((Node) loader.load());


            SingleSelectionModel<Tab> selectionModel = tabMenu.getSelectionModel();
            selectionModel.select(newShowEmployeeTab);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addEmployeePermission(Employee employee) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addPermissionScreen.fxml"));
            addNewTab((Node) loader.load(),"Pracownicy floty");

            AddPermisionController addPermisionController = loader.getController();
            addPermisionController.setDriver((Driver) employee);
            addPermisionController.createCurrentPermissions();
            addPermisionController.createNewPermission();


            /*
            Tab newShowEmployeeTab = new Tab("Dodawanie uprawnień");
            tabMenu.getTabs().add(newShowEmployeeTab);


            newShowEmployeeTab.setContent((Node) loader.load());



            SingleSelectionModel<Tab> selectionModel = tabMenu.getSelectionModel();
            selectionModel.select(newShowEmployeeTab);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void viewTasksList() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/addNewTask.fxml"));
            addNewTab((Node) loader.load(), "Lista zleceń");
            /*
            Tab newTaskTab = new Tab("Lista zleceń");
            tabMenu.getTabs().add(newTaskTab);

            newTaskTab.setContent((Node) loader.load());
            SingleSelectionModel<Tab> selectionModel = tabMenu.getSelectionModel();
            selectionModel.select(newTaskTab);*/
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void logOut() throws IOException {
        mainController.logout();
    }

    @FXML void changeSizeOfTrasyTable() throws IOException {
        double value = sizeOfTrasyTable.getValue();
        sizeOfTrasyTableTextField.setText(Double.toString(value));
    }

    public AnchorPane getWelcomePanel() {
        return welcomePanel;
    }
}
