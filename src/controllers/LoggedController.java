package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LoggedController extends Controller {

    @FXML private TabPane tabMenu;
    @FXML private ListView lastTasks;
    @FXML private SplitPane splitPane;
    private List<String> stringList  = new ArrayList<>(5);

    @FXML
    private Accordion accord;
    @FXML
    private TitledPane pane1;

    @FXML void closeAllTabs(){
        tabMenu.getTabs().clear();
    }

    @FXML void initialize() throws Exception {
        ObservableList observableList = FXCollections.observableArrayList();
        stringList.add("Przewóz materiałów");
        stringList.add("Transport sprzętu budowlanego do Kielc");
        stringList.add("Wywóz sprzętu budowlanego");
        stringList.add("Przewóz materiału");
        observableList.setAll(stringList);
        lastTasks.setItems(observableList);
        accord.setExpandedPane(pane1);
    }

    @FXML public void lastTasksClicked(MouseEvent arg0) {
        if(arg0.getButton().equals(MouseButton.PRIMARY)){
            if(arg0.getClickCount() == 2){
                System.out.println("clicked on " + lastTasks.getSelectionModel().getSelectedItem());
            }
        }
    }

    @FXML void addNewTask() throws IOException {
        try {
            Tab newTaskTab = new Tab("Utwórz nowe zlecenie");
            tabMenu.getTabs().add(newTaskTab);
            newTaskTab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/fxml/newTaskForm.fxml")));
            SingleSelectionModel<Tab> selectionModel = tabMenu.getSelectionModel();
            selectionModel.select(newTaskTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void showEmployee() throws IOException {
        try {
            Tab newShowEmployeeTab = new Tab("Pracownicy floty");
            tabMenu.getTabs().add(newShowEmployeeTab);
            newShowEmployeeTab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/fxml/showEmployeeForm.fxml")));
            SingleSelectionModel<Tab> selectionModel = tabMenu.getSelectionModel();
            selectionModel.select(newShowEmployeeTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void viewTasksList() throws IOException {
        try {
            Tab newTaskTab = new Tab("Lista zleceń");
            tabMenu.getTabs().add(newTaskTab);
            newTaskTab.setContent((Node) FXMLLoader.load(this.getClass().getResource("/fxml/newTaskForm.fxml")));
            SingleSelectionModel<Tab> selectionModel = tabMenu.getSelectionModel();
            selectionModel.select(newTaskTab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
