package Model;

public class Journalisation {
    private int id;
    private int utilisateurId;
    private String dateConnexion;

    public Journalisation() {
    }

    public Journalisation(int id, int utilisateurId, String dateConnexion) {
        this.id = id;
        this.utilisateurId = utilisateurId;
        this.dateConnexion = dateConnexion;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUtilisateurId() {
        return this.utilisateurId;
    }

    public void setUtilisateurId(int utilisateurId) {
        this.utilisateurId = utilisateurId;
    }

    public String getDateConnexion() {
        return this.dateConnexion;
    }

    public void setDateConnexion(String dateConnexion) {
        this.dateConnexion = dateConnexion;
    }

    public String toString() {
        return "Utilisateur ID: " + this.utilisateurId + ", Date: " + this.dateConnexion;
    }
}
