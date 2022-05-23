package AdminDashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminDeleteDriver {
    @FXML
    private Button deleteDriverButton,
            exitButton;

    public void deleteDriverButtonOnAction(ActionEvent event){
        Stage stage = (Stage) deleteDriverButton.getScene().getWindow();
        stage.close();
    }

    public void exitButtonOnAction(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
