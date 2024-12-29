package Model;

public class Utilisateur {
    private int id;
    private String nom;
    private String email;
    private String password;
    private byte[] image;
    private boolean statut;
    private String typeUser;

    public Utilisateur() {
    }


    public Utilisateur(int id, String nom, String email, String password, byte[] image, boolean statut, String typeUser) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.image = image;
        this.statut = statut;
        this.typeUser = typeUser;
    }

    public Utilisateur( String nom, String email, String password, byte[] image, boolean statut, String typeUser) {
        //this.id = id;
        this.nom = nom;
        this.email = email;
        this.password = password;
        this.image = image;
        this.statut = statut;
        this.typeUser = typeUser;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return this.image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isStatut() {
        return this.statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public String getTypeUser() {
        return this.typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }

    public String toString() {
        return this.nom + " (" + this.email + ")";
    }
}
