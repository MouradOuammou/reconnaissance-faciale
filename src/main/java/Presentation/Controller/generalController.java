package Presentation.Controller;
//import javafx.scene.layout.HBox;
import java.sql.SQLException;

import Model.Utilisateur;
import Traitement.UtilisateurTraitement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.scene.layout.GridPane;
import java.io.File;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class generalController {

    @FXML
    private TableView<Utilisateur> tableViewUser;
    @FXML
    private ComboBox<String> statutselected;
    @FXML
    private TextField nomfield, emailfield, passwordfield, typeuserfield;
    @FXML
    private ImageView imageUserview;
    @FXML
    private TableColumn<Utilisateur, Integer> idUserColumn;
    @FXML
    private TableColumn<Utilisateur, String> nomUserColumn, emailuserColumn, passworduserColumn, typeuserColumn;
    @FXML
    private TableColumn<Utilisateur, Boolean> statusUserColumn;

    private UtilisateurTraitement utilisateurTraitement;
    private ObservableList<Utilisateur> ListUsers;
    private byte[] selectedImageBytes;

    public void initialize() {
        utilisateurTraitement = new UtilisateurTraitement();
        setupColumnUser();
        LoadUtilisateur();
        statutselected.getItems().addAll("true", "false");
        tableViewUser.setOnMouseClicked(event -> {
            Utilisateur selectedUser = tableViewUser.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                remplirFormulaire(selectedUser);
            }
        });
    }

    public void setupColumnUser() {
        idUserColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomUserColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailuserColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        passworduserColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        typeuserColumn.setCellValueFactory(new PropertyValueFactory<>("typeUser"));
        statusUserColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
    }

    public void LoadUtilisateur() {
        try {
            ListUsers = FXCollections.observableArrayList(utilisateurTraitement.listUsers());
            tableViewUser.setItems(ListUsers);
        } catch (SQLException e) {
            afficherAlert("Erreur", e.getMessage(), "La récupération des utilisateurs a échoué");
        }
    }

    public void afficherAlert(String titre, String contenu, String header) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(header);
        alert.setContentText(contenu);
        alert.showAndWait();
    }

    @FXML
    public void importerImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Importer une image");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                selectedImageBytes = fis.readAllBytes();
                Image image = new Image(new ByteArrayInputStream(selectedImageBytes));
                imageUserview.setImage(image);
            } catch (IOException e) {
                afficherAlert("Erreur", "Impossible de charger l'image", e.getMessage());
            }
        }
    }

    public void remplirFormulaire(Utilisateur utilisateur) {
        nomfield.setText(utilisateur.getNom());
        emailfield.setText(utilisateur.getEmail());
        passwordfield.setText(utilisateur.getPassword());
        typeuserfield.setText(utilisateur.getTypeUser());
        statutselected.setValue(Boolean.toString(utilisateur.isStatut()));

        if (utilisateur.getImage() != null) {
            Image image = new Image(new ByteArrayInputStream(utilisateur.getImage()));
            imageUserview.setImage(image);
            selectedImageBytes = utilisateur.getImage();
        } else {
            imageUserview.setImage(null);
            selectedImageBytes = null;
        }
    }

    @FXML
    public void ajouterUtilisateur() {
        try {
            // Récupérer les données du formulaire
            String nom = nomfield.getText();
            String email = emailfield.getText();
            String password = passwordfield.getText();
            String typeUser = typeuserfield.getText();
            boolean statut = Boolean.parseBoolean(statutselected.getValue());

            // Vérifier que tous les champs sont remplis
            if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || typeUser.isEmpty() || selectedImageBytes == null) {
                afficherAlert("Erreur", "Veuillez remplir tous les champs et importer une image.", "Champs manquants");
                return;
            }

            // Vérifier si l'email existe déjà dans la base de données
            if (utilisateurTraitement.utilisateurExiste(email)) {
                afficherAlert("Erreur", "L'utilisateur avec cet email existe déjà.", "Email déjà utilisé");
                return;
            }

            // Créer un nouvel utilisateur
            Utilisateur newUser = new Utilisateur(nom, email, password, selectedImageBytes, statut, typeUser);

            // Ajouter l'utilisateur à la base de données
            utilisateurTraitement.addUtilisateur(newUser);

            // Réinitialiser le formulaire
            resetForm();

            // Rafraîchir la table
            LoadUtilisateur();
        } catch (SQLException e) {
            afficherAlert("Erreur", "Impossible d'ajouter l'utilisateur.", e.getMessage());
        }
    }

    @FXML
    public void modifierUtilisateur() {
        try {
            // Récupérer l'utilisateur sélectionné dans la table
            Utilisateur selectedUser = tableViewUser.getSelectionModel().getSelectedItem();

            if (selectedUser == null) {
                afficherAlert("Erreur", "Aucun utilisateur sélectionné.", "Veuillez sélectionner un utilisateur à modifier.");
                return;
            }

            // Récupérer les données modifiées depuis le formulaire
            String nom = nomfield.getText();
            String email = emailfield.getText();
            String password = passwordfield.getText();
            String typeUser = typeuserfield.getText();
            boolean statut = Boolean.parseBoolean(statutselected.getValue());

            // Vérifier que tous les champs sont remplis
            if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || typeUser.isEmpty() || selectedImageBytes == null) {
                afficherAlert("Erreur", "Veuillez remplir tous les champs et importer une image.", "Champs manquants");
                return;
            }

            // Vérifier si l'email existe déjà pour un autre utilisateur
            if (utilisateurTraitement.utilisateurExiste(email) && !selectedUser.getEmail().equals(email)) {
                afficherAlert("Erreur", "L'utilisateur avec cet email existe déjà.", "Email déjà utilisé");
                return;
            }

            // Mettre à jour les données de l'utilisateur
            selectedUser.setNom(nom);
            selectedUser.setEmail(email);
            selectedUser.setPassword(password);
            selectedUser.setTypeUser(typeUser);
            selectedUser.setStatut(statut);
            selectedUser.setImage(selectedImageBytes);

            // Envoyer la mise à jour à la base de données
            utilisateurTraitement.updateUtilisateur(selectedUser);

            // Réinitialiser le formulaire
            resetForm();

            // Rafraîchir la table
            LoadUtilisateur();
        } catch (SQLException e) {
            afficherAlert("Erreur", "Impossible de modifier l'utilisateur.", e.getMessage());
        }
    }


//    @FXML
//    public void ajouterUtilisateur() {
//        try {
//            // Récupérer les données du formulaire
//            String nom = nomfield.getText();
//            String email = emailfield.getText();
//            String password = passwordfield.getText();
//            String typeUser = typeuserfield.getText();
//            boolean statut = Boolean.parseBoolean(statutselected.getValue());
//
//            // Vérifier que tous les champs sont remplis
//            if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || typeUser.isEmpty() || selectedImageBytes == null) {
//                afficherAlert("Erreur", "Veuillez remplir tous les champs et importer une image.", "Champs manquants");
//                return;
//            }
//
//            // Créer un nouvel utilisateur
//            Utilisateur newUser = new Utilisateur(nom, email, password, selectedImageBytes, statut, typeUser);
//
//            // Ajouter l'utilisateur à la base de données
//            utilisateurTraitement.addUtilisateur(newUser);
//
//            // Réinitialiser le formulaire
//            resetForm();
//
//            // Rafraîchir la table
//            LoadUtilisateur();
//        } catch (SQLException e) {
//            afficherAlert("Erreur", "Impossible d'ajouter l'utilisateur.", e.getMessage());
//        }
//    }
//
//    @FXML
//    public void modifierUtilisateur() {
//        try {
//            // Récupérer l'utilisateur sélectionné dans la table
//            Utilisateur selectedUser = tableViewUser.getSelectionModel().getSelectedItem();
//
//            if (selectedUser == null) {
//                afficherAlert("Erreur", "Aucun utilisateur sélectionné.", "Veuillez sélectionner un utilisateur à modifier.");
//                return;
//            }
//
//            // Récupérer les données modifiées depuis le formulaire
//            String nom = nomfield.getText();
//            String email = emailfield.getText();
//            String password = passwordfield.getText();
//            String typeUser = typeuserfield.getText();
//            boolean statut = Boolean.parseBoolean(statutselected.getValue());
//
//            // Vérifier que tous les champs sont remplis
//            if (nom.isEmpty() || email.isEmpty() || password.isEmpty() || typeUser.isEmpty() || selectedImageBytes == null) {
//                afficherAlert("Erreur", "Veuillez remplir tous les champs et importer une image.", "Champs manquants");
//                return;
//            }
//
//            // Mettre à jour les données de l'utilisateur
//            selectedUser.setNom(nom);
//            selectedUser.setEmail(email);
//            selectedUser.setPassword(password);
//            selectedUser.setTypeUser(typeUser);
//            selectedUser.setStatut(statut);
//            selectedUser.setImage(selectedImageBytes);
//
//            // Envoyer la mise à jour à la base de données
//            utilisateurTraitement.updateUtilisateur(selectedUser);
//
//            // Réinitialiser le formulaire
//            resetForm();
//
//            // Rafraîchir la table
//            LoadUtilisateur();
//        } catch (SQLException e) {
//            afficherAlert("Erreur", "Impossible de modifier l'utilisateur.", e.getMessage());
//        }
//    }

    @FXML
    public void supprimerUtilisateur() {
        try {
            // Récupérer l'utilisateur sélectionné dans la table
            Utilisateur selectedUser = tableViewUser.getSelectionModel().getSelectedItem();

            if (selectedUser == null) {
                afficherAlert("Erreur", "Aucun utilisateur sélectionné.", "Veuillez sélectionner un utilisateur à supprimer.");
                return;
            }

            // Afficher une confirmation avant la suppression
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirmation de suppression");
            confirmationAlert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet utilisateur ?");
            confirmationAlert.setContentText("Cette action est irréversible.");
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        // Supprimer l'utilisateur de la base de données
                        utilisateurTraitement.deleteUtilisateur(selectedUser);

                        // Rafraîchir la table
                        LoadUtilisateur();

                        // Réinitialiser le formulaire
                        resetForm();
                    } catch (SQLException e) {
                        afficherAlert("Erreur", "Impossible de supprimer l'utilisateur.", e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            afficherAlert("Erreur", "Une erreur inattendue s'est produite.", e.getMessage());
        }
    }

    public void resetForm() {
        nomfield.clear();
        emailfield.clear();
        passwordfield.clear();
        typeuserfield.clear();
        statutselected.setValue(null);
        imageUserview.setImage(null);
        selectedImageBytes = null;
    }
}
