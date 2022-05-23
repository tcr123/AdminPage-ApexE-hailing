package AdminDashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLoginPageController {
    @FXML
    private Button loginButton;
    @FXML
    private Button exitButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label loginMessage;

    public void loginButtonOnAction(ActionEvent event) throws IOException {
        if ((usernameField.getText().isBlank())&&(passwordField.getText().isBlank())){
            loginMessage.setText("Please enter UserID and password!");
        }
        else if (passwordField.getText().isBlank()){
            loginMessage.setText("Please enter password!");
        }
        else if (usernameField.getText().isBlank()){
            loginMessage.setText("Please enter UserID!");
        }
        else{
//            Connection con = AdminMain.getConnection();
//            String id = usernameField.getText();
//            String password = passwordField.getText();
//            PreparedStatement statement = con.prepareStatement("SELECT COUNT(username) AS existence FROM tablename WHERE ");
//            ResultSet exist = statement.executeQuery();
//            while(exist.next()){
//                if ((exist.getInt("got")) == 1){
//
//                } else {
//                    loginMessage.setText("Admin account not found!");
//                }
//            }
        }
    }

    public void exitButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    //for testing purpose only
    public void accessToMenuPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminMenuPage.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
