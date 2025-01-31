package teams.reconnaissance.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import java.awt.image.BufferedImage;
import java.io.File;

public class TestVisageController {

    @FXML
    private ImageView cameraView;
    @FXML
    private Label faceCountLabel; // Label pour afficher le nombre de visages détectés

    private Mat lastCapturedFrame;
    private VideoCapture videoCapture;
    private boolean capturing;
    private boolean saveRequested = false;

    private static final File DATABASE = new File("Database"); // Directory to save faces

    @FXML
    public void initialize() {
        System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME); // Load OpenCV library
        videoCapture = new VideoCapture();
        capturing = true;
        openCamera(); // Automatically open the camera on initialization
    }

    @FXML
    public void openCamera() {
        if (!videoCapture.open(0)) { // Open the default camera
            showAlert("Erreur", "Impossible d'ouvrir la caméra.");
            return;
        }

        Thread frameGrabber = new Thread(() -> {
            Mat frame = new Mat();
            CascadeClassifier faceDetector = new CascadeClassifier("lbpcascade_frontalface_improved.xml");

            while (capturing) {
                if (videoCapture.read(frame)) {
                    if (frame.empty()) {
                        stopCamera();
                        showAlert("Erreur", "Flux vidéo vide.");
                        break;
                    }
                    // Process the frame: detect faces and convert color
                    frame = detectFaces(frame, faceDetector);
                    Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB);
                    Image image = matToImage(frame);

                    // Safely update the ImageView from the JavaFX Application thread
                    Platform.runLater(() -> cameraView.setImage(image));

                    try {
                        Thread.sleep(30); // Pause for synchronization (30ms)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    stopCamera();
                    showAlert("Erreur", "Problème de lecture du flux vidéo.");
                    break;
                }
            }
        });

        frameGrabber.setDaemon(true); // Ensure the thread stops when the application exits
        frameGrabber.start();
    }

    private Mat detectFaces(Mat image, CascadeClassifier faceDetector) {
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);
        Rect[] faces = faceDetections.toArray();

        // Mettre à jour le Label avec le nombre de visages détectés
        int faceCount = faces.length;
        Platform.runLater(() -> faceCountLabel.setText("Visages détectés: " + faceCount));

        for (Rect face : faces) {
            Mat croppedImage = new Mat(image, face);

            // Vérifier si l'utilisateur a demandé une sauvegarde
            if (saveRequested) {
                String name = "Person" + System.currentTimeMillis();
                saveImage(croppedImage, name); // Sauvegarder uniquement si demandé
                saveRequested = false; // Réinitialiser après la sauvegarde
            }

            // Dessiner un rectangle autour du visage et afficher "ID: Unknown"
            Imgproc.putText(image, "ID: Unknown", face.tl(), Imgproc.FONT_HERSHEY_SIMPLEX, 1.5, new Scalar(255, 0, 0), 2);
            Imgproc.rectangle(image, face.tl(), face.br(), new Scalar(0, 255, 0)); // Dessiner un rectangle autour du visage
        }

        return image;
    }

    private void saveImage(Mat image, String name) {
        // Vérifier si le dossier 'Database' existe, sinon créer
        if (!DATABASE.exists()) {
            boolean folderCreated = DATABASE.mkdir(); // Crée le dossier si nécessaire
            if (folderCreated) {
                System.out.println("Dossier Database créé avec succès.");
            } else {
                System.out.println("Échec de la création du dossier Database.");
                showAlert("Erreur", "Échec de la création du dossier Database.");
                return; // Ne pas poursuivre si le dossier ne peut pas être créé
            }
        }

        // Définir le nom du fichier de l'image
        String fileName = name + ".png";
        File file = new File(DATABASE, fileName);

        // Sauvegarder l'image
        boolean success = Imgcodecs.imwrite(file.getAbsolutePath(), image);
        if (success) {
            System.out.println("Image sauvegardée sous: " + file.getAbsolutePath());
            showAlert("Succès", "Image sauvegardée sous : " + file.getAbsolutePath());
        } else {
            System.out.println("Échec de la sauvegarde de l'image.");
            showAlert("Erreur", "Échec de la sauvegarde de l'image.");
        }
    }


    @FXML
    public void onSaveButtonClick() {
        saveRequested = true; // Activer la demande de sauvegarde
        showAlert("Info", "Vous pouvez capturer une image à sauvegarder.");
    }

    @FXML
    public void stopCamera() {
        capturing = false;
        if (videoCapture.isOpened()) {
            videoCapture.release();
        }
    }

    @FXML
    public void handleClose() {
        stopCamera();
        Platform.exit();
    }

    private void showAlert(String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    private Image matToImage(Mat frame) {
        BufferedImage bufferedImage = matToBufferedImage(frame);
        return javafx.embed.swing.SwingFXUtils.toFXImage(bufferedImage, null);
    }

    private BufferedImage matToBufferedImage(Mat frame) {
        int width = frame.width();
        int height = frame.height();
        int channels = frame.channels();
        byte[] sourcePixels = new byte[width * height * channels];
        frame.get(0, 0, sourcePixels);

        BufferedImage image;
        if (channels == 1) {
            image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        } else {
            image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        }
        image.getRaster().setDataElements(0, 0, width, height, sourcePixels);
        return image;
    }

    public void saveFace(ActionEvent actionEvent) {
        saveRequested = true; // Indique que la sauvegarde est demandée
        showAlert("Info", "Vous pouvez capturer une image à sauvegarder.");
    }
}
