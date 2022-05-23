package AdminDashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminAddDriver {
    @FXML
    private Button addDriverButton,
            exitButton;

    public void addDriverButtonOnAction(ActionEvent event){
        Stage stage = (Stage) addDriverButton.getScene().getWindow();
        stage.close();
    }

    public void exitButtonOnAction(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
