package com.example.ehailingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

    public void loginButtonOnAction(ActionEvent event){
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
            //code for validate login
        }
    }

    public void exitButtonOnAction(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
}
