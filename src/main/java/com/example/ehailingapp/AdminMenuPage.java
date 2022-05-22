package com.example.ehailingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminMenuPage implements Initializable{

    @FXML
    private ListView<String> menuList;
    @FXML
    private ImageView backwardButtonImage;
    @FXML
    private Button backwardButton;
    @FXML
    private ImageView warningIcon;

    String[] menu = {"A", "B", "C"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        menuList.getItems().addAll(menu);

        File file1 = new File("Picture/undoIcon.png");
        Image image1 = new Image(file1.toURI().toString());
        backwardButtonImage.setImage(image1);

        File file2 = new File("Picture/warningIcon.png");
        Image image2 = new Image(file2.toURI().toString());
        warningIcon.setImage(image2);
    }

    public void backwardButtonOnClick(ActionEvent event, Stage stage) throws IOException {
        promptLogoutConfirmation(event, stage);
    }

    public void promptLogoutConfirmation(ActionEvent event, Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("logoutAlert.fxml"));
        stage.setTitle("Warning");
        stage.setScene(new Scene(root,750,600));
        stage.show();
    }
}
