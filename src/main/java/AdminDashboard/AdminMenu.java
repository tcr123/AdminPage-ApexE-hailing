package AdminDashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminMenu implements Initializable{

    @FXML
    private ListView<String> menuList;
    @FXML
    private ImageView backwardButtonImage;
    @FXML
    private Button backwardButton,
            logoutYesButton,
            logoutNoButton,
            customerListButton,
            driverListButton,
            mapButton;
    @FXML
    private ImageView warningIcon;

    String[] menu = {"A", "B", "C"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        menuList.getItems().addAll(menu);
    }

    public void backwardButtonOnClick(ActionEvent event) throws IOException {
        promptLogoutConfirmation(event);
    }

    public void driverListButtonOnAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("adminDriverListPage.fxml"));
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logoutYes(ActionEvent event) throws IOException {
        Stage stage = (Stage) logoutNoButton.getScene().getWindow();
        stage.close();
        Parent root = FXMLLoader.load(getClass().getResource("adminLoginPage.fxml"));
        stage = (Stage) backwardButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logoutNo(ActionEvent event){
        Stage stage = (Stage) logoutNoButton.getScene().getWindow();
        stage.close();
    }

    public void promptLogoutConfirmation(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("logoutAlert.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Warning!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(backwardButton.getScene().getWindow());
        stage.show();
    }

}
