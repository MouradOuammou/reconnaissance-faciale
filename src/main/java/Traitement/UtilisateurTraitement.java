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
}
