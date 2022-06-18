package AdminDashboard;

import DbAccess.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminDeleteCustomer {
    @FXML
    private Button deleteCustomerButton,
            exitButton;
    @FXML
    private Label alert_message;
    @FXML
    private TextField name_field;

    public void deleteCustomerButtonOnAction(ActionEvent event) throws Exception {
        Connection conn = DBConnector.getConnection();
        String name = name_field.getText();
        PreparedStatement statement = conn.prepareStatement("SELECT COUNT(username) AS got FROM user WHERE username = '"+name+"'");
        ResultSet rs = statement.executeQuery();
        int got = 0;
        while (rs.next()){
            got = rs.getInt("got");
        }
        if (got == 0){
            alert_message .setText("Customer \""+name+"\" not found!");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer Confirmation");
            alert.setHeaderText("Delete customer \"" + name +"\"");
            alert.setContentText("Do you want to delete the customer?");
            if (alert.showAndWait().get() == ButtonType.OK) {
                statement = conn.prepareStatement("DELETE FROM user WHERE username = '"+name+"'");
                statement.executeUpdate();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Delete Customer");
                alert.setHeaderText("Customer "+name+" has been deleted!");
                alert.showAndWait();
            }
            Stage stage = (Stage) deleteCustomerButton.getScene().getWindow();
            stage.close();
        }
    }

    public void exitButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
