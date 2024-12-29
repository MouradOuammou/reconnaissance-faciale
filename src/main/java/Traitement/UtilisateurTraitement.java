package Traitement;

import Model.Utilisateur;
import database.UtilisateursDB;

import java.sql.SQLException;
import java.util.List;

public class UtilisateurTraitement {
    private static UtilisateursDB utilisateursDB;

    public UtilisateurTraitement(){
        this.utilisateursDB=new UtilisateursDB();
    }

    public List<Utilisateur> listUsers() throws SQLException {
        return utilisateursDB.getAllUtilisateurs();
    }

    // Ajouter un utilisateur à la base de données
    public void addUtilisateur(Utilisateur utilisateur) throws SQLException {
        if (utilisateur == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
        }
        utilisateursDB.addUtilisateur(utilisateur);
    }

    // Méthode pour mettre à jour un utilisateur
    public void updateUtilisateur(Utilisateur utilisateur) throws SQLException {
        if (utilisateur == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
        }
        utilisateursDB.updateUtilisateur(utilisateur);
    }

    // Méthode pour supprimer un utilisateur
    public void deleteUtilisateur(Utilisateur utilisateur) throws SQLException {
        if (utilisateur == null) {
            throw new IllegalArgumentException("L'utilisateur ne peut pas être null");
        }
        utilisateursDB.deleteUtilisateur(utilisateur);
    }

    // Vérifier si un utilisateur existe par email
    public boolean utilisateurExiste(String email) throws SQLException {
        return utilisateursDB.utilisateurExiste(email);
    }


}
