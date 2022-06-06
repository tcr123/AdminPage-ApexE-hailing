package AdminDashboard;

import DbAccess.DBConnector;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLogin extends Application {

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

    private static String adminName = "";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdminLogin.class.getResource("adminLoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 600);
//        stage.setTitle("Apex E-Hailing");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });
    }

    public static void accessAdminLoginPage() {
        launch();
    }

    public void accessToMenuPage(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminMenuPage.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void loginButtonOnAction(ActionEvent event) throws Exception {
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
            Connection con = DBConnector.getConnection();
            String name = usernameField.getText();
            String password = passwordField.getText();
            PreparedStatement statement = con.prepareStatement("SELECT * FROM authenication WHERE name = '"+name+"' AND password = '" + password +"'");
            ResultSet rs = statement.executeQuery();
            String dbName = "", dbPassword = "";
            while (rs.next()) {
                dbName = rs.getString("name");
                dbPassword = rs.getString("password");
            }
            if (!name.equals(dbName)){
                loginMessage.setText("Admin account '"+ name +"' not found!");
            } else {
                if (!password.equals(dbPassword)){
                    loginMessage.setText("Wrong password! Please try again!");
                } else {
                    AdminMenu.adminName = dbName;
                    accessToMenuPage(event);
                }
            }
        }
    }

    public void exit(Stage stage){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("Exit confirmation");
        alert.setContentText("Do you want to exit?: ");
        if (alert.showAndWait().get() == ButtonType.OK){
            stage.close();
        }
    }

    public void exitButtonOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        exit(stage);
    }
}
