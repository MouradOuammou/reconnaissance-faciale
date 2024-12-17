package teams.reconnaisance.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.io.File;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;

public class TestVisageController {

    @FXML
    private ImageView cameraView;
    private Mat lastCapturedFrame;
    private VideoCapture videoCapture;
    private boolean capturing;

    @FXML
    public void initialize() {
        // Charger la bibliothèque OpenCV
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);

        // Initialiser la caméra
        videoCapture = new VideoCapture();
        capturing = true;

        // Démarrer automatiquement la caméra
        openCamera();
    }

    @FXML
    public void openCamera() {
        if (!videoCapture.open(0)) {  // Ouvre la caméra par défaut
            System.err.println("Erreur : Impossible d'ouvrir la caméra.");
            return;
        }

        // Thread pour capturer et afficher les frames
        Thread frameGrabber = new Thread(() -> {
            Mat frame = new Mat();
            while (capturing) {
                if (videoCapture.read(frame)) {
                    // Convertir BGR en RGB
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB);
                    // Convertir le Mat en Image JavaFX
                    Image image = matToImage(frame);
                    Platform.runLater(() -> cameraView.setImage(image));  // Mettre à jour l'ImageView
                }
            }
        });

        frameGrabber.setDaemon(true);  // Arrêter le thread si l'application se ferme
        frameGrabber.start();
    }

    @FXML
    public void saveImage() {
        if (lastCapturedFrame != null) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer l'image");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));

            // Récupérer la fenêtre principale
            javafx.stage.Window stage = cameraView.getScene().getWindow();

            // Afficher la boîte de dialogue pour choisir l'emplacement
            File file = fileChooser.showSaveDialog(stage);
            if (file != null) {
                String filePath = file.getAbsolutePath();
                // Sauvegarder l'image au chemin sélectionné
                Imgcodecs.imwrite(filePath, lastCapturedFrame);
                showAlert("Succès", "L'image a été sauvegardée sous : " + filePath);
            } else {
                showAlert("Info", "Aucun fichier sélectionné.");
            }
        } else {
            showAlert("Erreur", "Aucune image à sauvegarder. Capturez une image d'abord.");
        }
    }

    @FXML
        public void stopCamera() {
        capturing = false;
        if (videoCapture.isOpened()) {
            videoCapture.release();  // Libérer la caméra
            System.out.println("Caméra arrêtée.");
        }
    }

    // Méthode pour convertir un Mat (OpenCV) en Image JavaFX
    private Image matToImage(Mat frame) {
        BufferedImage bufferedImage = matToBufferedImage(frame);
        return javafx.embed.swing.SwingFXUtils.toFXImage(bufferedImage, null);
    }

    // Méthode pour convertir un Mat en BufferedImage
    private BufferedImage matToBufferedImage(Mat frame) {
        int width = frame.width();
        int height = frame.height();
        int channels = frame.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        frame.get(0, 0, sourcePixels);
        BufferedImage image;
        if (frame.channels() == 1) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        } else {
            image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        }

        image.getRaster().setDataElements(0, 0, width, height, sourcePixels);
        return image;
    }

    public void captureImage(ActionEvent event) {
        lastCapturedFrame = new Mat();
        if (videoCapture.read(lastCapturedFrame)) {
            Imgproc.cvtColor(lastCapturedFrame, lastCapturedFrame, Imgproc.COLOR_BGR2RGB);

            // Afficher un message de confirmation
            showAlert("Image Capturée", "L'image a été capturée. Cliquez sur 'Reprendre' pour une nouvelle capture ou sauvegardez.");
        } else {
            showAlert("Erreur", "Impossible de capturer l'image.");
        }

    }

    @FXML
    public void retryCapture(ActionEvent event) {
        if (lastCapturedFrame != null) {
            lastCapturedFrame.release(); // Libérer l'image précédente
        }
        lastCapturedFrame = null;
        showAlert("Reprise", "Vous pouvez capturer une nouvelle image.");
    }

    private void showAlert(String title, String message) {
        // Afficher une alerte JavaFX
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
