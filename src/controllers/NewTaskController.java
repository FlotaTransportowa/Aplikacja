package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class NewTaskController extends  Controller
{
    @FXML private TextField newTaskTitle;
    //@FXML private DataPicker newTaskDate;
    @FXML private ChoiceBox newTaskType;
    @FXML private ChoiceBox newTaskLocation;
    @FXML private TextArea newTaskComment;
    @FXML private Text newTaskStatus;

    @FXML void initialize() throws Exception
    {
    }

    @FXML void cancelTask() throws IOException
    {

    }

    @FXML void addTask() throws IOException
    {
    }
}
