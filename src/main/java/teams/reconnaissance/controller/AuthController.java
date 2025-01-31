package teams.reconnaissance.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class AuthController {

    public void CreateCamera(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/teams/TestVisageView.fxml"));
            Parent TestVisageView = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(TestVisageView));
            stage.setTitle("Test Visage");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

