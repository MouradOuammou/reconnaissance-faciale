package Presentation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/fxml/UserView.fxml"));
        Scene scene=new Scene(loader.load(),800,600);
        stage.setScene(scene);
        stage.setTitle("Liste des Utilisateurs");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
