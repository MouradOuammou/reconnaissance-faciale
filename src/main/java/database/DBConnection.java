package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Chemin vers la base de données SQLite (ici, 'employee_db.sqlite' dans le dossier du projet)
    private static final String url = "jdbc:sqlite:Reconnaissance_Facial.sqlite";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
