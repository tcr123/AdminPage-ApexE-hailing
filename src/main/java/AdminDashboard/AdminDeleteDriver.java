package AdminDashboard;

import DbAccess.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDeleteDriver {
    @FXML
    private Button deleteDriverButton,
            exitButton;
    @FXML
    private Label alert_message;
    @FXML
    private TextField name_field;

    public void deleteDriverButtonOnAction(ActionEvent event) throws Exception {
        Connection conn = DBConnector.getConnection();
        String name = name_field.getText();
        PreparedStatement statement = conn.prepareStatement("SELECT COUNT(name) AS got FROM driver WHERE name = '"+name+"'");
        ResultSet rs = statement.executeQuery();
        int got = 0;
        while (rs.next()){
            got = rs.getInt("got");
        }
        if (got == 0){
            alert_message .setText("Driver \""+name+"\" not found!");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Driver Confirmation");
            alert.setHeaderText("Delete driver \"" + name +"\"");
            alert.setContentText("Do you want to delete the driver?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                statement = conn.prepareStatement("DELETE FROM driver WHERE name = '"+name+"'");
                statement.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Driver");
                alert.setHeaderText("Driver "+name+" has been deleted!");
                alert.showAndWait();
            }
            Stage stage = (Stage) deleteDriverButton.getScene().getWindow();
            stage.close();
        }
    }

    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
