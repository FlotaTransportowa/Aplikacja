package sample;

import database.ConnectionConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/mainScreen.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("FSB - System zarzadzania");
        Scene scene = new Scene(root, 1280, 850);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        scene.getStylesheets().add(getClass().getResource("/resources/general.css").toExternalForm());
        ConnectionConfig.connectDB();
    }

    public static void main(String[] args) {launch(args);}
}
