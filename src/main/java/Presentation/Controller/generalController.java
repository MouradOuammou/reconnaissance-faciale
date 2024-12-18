package Presentation.Controller;

import Model.Utilisateur;
import Traitement.UtilisateurTraitement;
import javafx.collections.ObservableList;  // Remplacez cette importation
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.sql.SQLException;

public class generalController {
    @FXML
    private Label GSU;
    @FXML
    private TableView tableViewUser;
    @FXML
    private GridPane FormUser;
    @FXML
    private ComboBox statutselected;
    @FXML
    private Label nomUser;
     @FXML
    private Label emailUser;
     @FXML
    private Label passwordUser;
     @FXML
    private Label typeUser;
     @FXML
    private Label statusUser;
     @FXML
    private Label imageUser;
     @FXML
    private TextField nomfield;
     @FXML
    private TextField typeuserfield;
     @FXML
    private TextField emailfield;
     @FXML
    private TextField passwordfield;
    @FXML
    private ImageView imageUserview;
    @FXML
    private HBox vboxUser;
     @FXML
    private TableColumn idUserColumn;
   @FXML
    private TableColumn emailuserColumn;
   @FXML
    private TableColumn passworduserColumn;
   @FXML
    private TableColumn typeuserColumn;
   @FXML
    private TableColumn statusUserColumn;
   @FXML
    private TableColumn nomUserColumn;
   @FXML
    private Button btnAdd;
  @FXML
    private Button btnUpdate;
  @FXML
    private Button btnDelete;

  private UtilisateurTraitement utilisateurTraitement;
  private ObservableList<Utilisateur> ListUsers;

    public void initialize(){
        utilisateurTraitement=new UtilisateurTraitement();
        setupColumnUser();
        LoadUtilisateur();
    }

    public void setupColumnUser(){
        idUserColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomUserColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        typeuserColumn.setCellValueFactory(new PropertyValueFactory<>("typeUser"));
        statusUserColumn.setCellValueFactory(new PropertyValueFactory<>("statut"));
        passworduserColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        emailuserColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    public  void LoadUtilisateur(){
        try {
            ListUsers=FXCollections.observableArrayList(utilisateurTraitement.listUsers());
            tableViewUser.setItems(ListUsers);
        } catch (SQLException e) {
            afficherAlert("Erreur",e.getMessage(),"La récupération des Utilisateurs a échoué");
        }
    }

    public void afficherAlert(String titre,String contenu,String header){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(header);
        alert.setContentText(contenu);
        alert.showAndWait();
    }
}
