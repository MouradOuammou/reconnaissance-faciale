import sqlite3
import os

# Connexion à la base de données
conn = sqlite3.connect('Reconnaissance_Facial.sqlite')
cursor = conn.cursor()

# Liste des utilisateurs avec leurs chemins d'image (mis à jour avec le nouveau chemin)
utilisateurs = [
    ("Elon Musk", "ElonMusk@gmail.com", "Elon_Musk", r"C:\Users\lenovo\OneDrive\Desktop\CRUDUSER\src\main\photos\Elon_Musk.jpg", 0),
    ("Azize Akhannoche", "AzizeAkhannoche@gmail.com", "Azize_Akhannoche", r"C:\Users\lenovo\OneDrive\Desktop\CRUDUSER\src\main\photos\Akhannoche.jpg", 0),
    ("Omar Lotfi", "OmarLotfi@gmail.com", "Omar_Lotfi", r"C:\Users\lenovo\OneDrive\Desktop\CRUDUSER\src\main\photos\omar_lotfi.jpg", 0)
]

# Insérer chaque utilisateur
for nom, email, password, chemin_image, statut in utilisateurs:
    # Vérifiez si le fichier image existe avant de le lire
    if os.path.exists(chemin_image):
        # Lire l'image comme données binaires
        with open(chemin_image, 'rb') as file:
            image_data = file.read()

        # Exécuter l'insertion
        cursor.execute(''' 
            INSERT INTO Utilisateur (nom, email, password, image, statut)
            VALUES (?, ?, ?, ?, ?)
        ''', (nom, email, password, image_data, statut))
    else:
        print(f"L'image pour {nom} n'a pas été trouvée : {chemin_image}")

# Sauvegarder les changements et fermer la connexion
conn.commit()
conn.close()

print("Les données ont été insérées avec succès.")
