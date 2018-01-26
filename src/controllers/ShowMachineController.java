package controllers;

import database.Machine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.TableView;
import manager.GlobalManager;
import models.MachineModel;

import javax.persistence.EntityManager;
import java.io.IOException;

public class ShowMachineController {
    private LoggedController loggedController;

    @FXML private TableView<Machine> machineTable;
    private static ObservableList<Machine> data;
    private MachineModel machineModel = new MachineModel();

    @FXML void initialize() throws Exception {
        data = FXCollections.observableArrayList(machineModel.getAll());
        machineTable.setItems(data);


        for (
                Machine e : data
                ) {
            ButtonBar buttonBar = e.getButtonBar();
            if (buttonBar.getButtons().size() > 0)
                continue;
            Button editButton = new Button("Edytuj");
            editButton.setOnAction(ev -> {
                        if (e == null)
                            System.out.println(e.getId());
                        else
                            try {
                                loggedController.editMachine(e);
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                    }
            );
            Button deleteButton = new Button("Usuń");
            deleteButton.setOnAction(ev -> {
                        EntityManager entityManager = GlobalManager.getManager();
                        entityManager.getTransaction().begin();
                        try {
                            entityManager.remove(e);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            entityManager.getTransaction().commit();
                        }
                        System.out.println("Usunięto " + e.getId());
                        try {
                            initialize();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                    }
            );

            ButtonBar.setButtonData(editButton, ButtonBar.ButtonData.BIG_GAP);
            ButtonBar.setButtonData(deleteButton, ButtonBar.ButtonData.BIG_GAP);
            buttonBar.getButtons().addAll(editButton, deleteButton);
        }
    }

    public void setLoggedController(LoggedController loggedController){
        this.loggedController = loggedController;
    }

}
