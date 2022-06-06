package AdminDashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminMap{
    @FXML
    private Button backwardButton,
            exitButton;
    @FXML
    private Hyperlink toMenuHyperlink;

    public void exit(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit confirmation");
        alert.setContentText("Do you want to exit?: ");
        if (alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }

    public void toMenuPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminMenuPage.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
