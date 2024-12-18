package database;

import Model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateursDB {

    // Méthode pour obtenir tous les utilisateurs
    public List<Utilisateur> getAllUtilisateurs() throws SQLException {
        String query = "SELECT * FROM Utilisateur";
        List<Utilisateur> listUtilisateur = new ArrayList<>();

        try (Connection cnx = DBConnection.getConnection();
             Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // Parcours des résultats de la requête
            while (rs.next()) {
                // Création d'un objet Utilisateur pour chaque ligne de résultat
                Utilisateur utilisateur = new Utilisateur(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getBytes("image"),  // Récupérer l'image sous forme de tableau de bytes
                        rs.getBoolean("statut"),
                        rs.getString("typeuser")
                );
                // Ajout de l'utilisateur à la liste
                listUtilisateur.add(utilisateur);
            }
        }

        // Retourner la liste des utilisateurs
        return listUtilisateur;
    }

    // Méthode pour vérifier si un utilisateur existe par email
    private boolean utilisateurExiste(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM Utilisateur WHERE email = ?";
        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(query)) {

            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    // Méthode pour ajouter un nouvel utilisateur après vérification de son existence
    public void addUtilisateur(Utilisateur utilisateur) throws SQLException {
        // Vérifier si l'utilisateur existe déjà par son email
        if (utilisateurExiste(utilisateur.getEmail())) {
            System.out.println("L'utilisateur avec cet email existe déjà.");
            return;  // Ne pas ajouter l'utilisateur si il existe déjà
        }

        String query = "INSERT INTO Utilisateur (nom, email, password, image, statut, typeuser) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(query)) {

            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getPassword());
            pstmt.setBytes(4, utilisateur.getImage());
            pstmt.setBoolean(5, utilisateur.isStatut());
            pstmt.setString(6, utilisateur.getTypeUser());

            pstmt.executeUpdate(); // Exécution de l'insertion
            System.out.println("Utilisateur ajouté avec succès!");
        }
    }

    // Méthode pour mettre à jour un utilisateur après vérification de son existence
    public void updateUtilisateur(Utilisateur utilisateur) throws SQLException {

        String query = "UPDATE Utilisateur SET nom = ?, email = ?, password = ?, image = ?, statut = ?, typeuser = ? WHERE id = ?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(query)) {

            pstmt.setString(1, utilisateur.getNom());
            pstmt.setString(2, utilisateur.getEmail());
            pstmt.setString(3, utilisateur.getPassword());
            pstmt.setBytes(4, utilisateur.getImage());
            pstmt.setBoolean(5, utilisateur.isStatut());
            pstmt.setString(6, utilisateur.getTypeUser());
            pstmt.setInt(7, utilisateur.getId());  // L'ID de l'utilisateur à mettre à jour

            pstmt.executeUpdate(); // Exécution de la mise à jour
            System.out.println("Utilisateur mis à jour avec succès!");
        }
    }

    // Méthode pour supprimer un utilisateur
    public void deleteUtilisateur(int id) throws SQLException {

        String query = "DELETE FROM Utilisateur WHERE id = ?";

        try (Connection cnx = DBConnection.getConnection();
             PreparedStatement pstmt = cnx.prepareStatement(query)) {

            pstmt.setInt(1, id);  // L'ID de l'utilisateur à supprimer
            pstmt.executeUpdate(); // Exécution de la suppression
            System.out.println("Utilisateur supprimé avec succès!");
        }
    }
}
