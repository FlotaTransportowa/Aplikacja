package controllers;

import fxModels.NotificationFX;
import fxModels.TrackFX;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.table.TableRowExpanderColumn;

public class ShowAllNotificationsController extends Controller {
    @FXML
    private TextField searchField;
    @FXML private TableView<NotificationFX> tableView;
    private static ObservableList<NotificationFX> data;

    private void initialize()
    {
        data = NotificationFX.getAll();

//        TableRowExpanderColumn<NotificationFX> expander = new TableRowExpanderColumn<NotificationFX>(this::createTrackExpander);
//        expander.setMinWidth(30);
//        expander.setMaxWidth(30);
    }

}
