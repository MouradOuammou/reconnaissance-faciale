package teams.reconnaisance.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class AuthController {

    public void CreateCamera(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teams/reconnaisance/TestVisageView.fxml"));
            Parent TestVisageView = loader.load();
            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Définir la nouvelle scène sur le stage
            stage.setScene(new Scene(TestVisageView));
            stage.setTitle("Test Visage");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

