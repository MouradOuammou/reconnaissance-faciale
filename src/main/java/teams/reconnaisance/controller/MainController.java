package teams.reconnaisance.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    public void btnConnexion(ActionEvent event) {
        try {
            // Charger le fichier FXML de la scène AuthView
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teams/reconnaisance/AuthView.fxml"));
            Parent authView = loader.load();

            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur le stage
            stage.setScene(new Scene(authView));
            stage.setTitle("Authentification"); // Facultatif : définir un titre pour la fenêtre
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnInscrire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teams/reconnaisance/InscriptionView.fxml"));
            Parent InscriptionView = loader.load();
            // Obtenir le stage actuel
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            // Définir la nouvelle scène sur le stage
            stage.setScene(new Scene(InscriptionView));
            stage.setTitle("Inscription");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}