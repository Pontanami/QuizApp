package com.example.quizapp.controllers;

import com.example.quizapp.user.FirebaseUserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginController extends AnchorPane{
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();
    @FXML
    AnchorPane rootpane;
    //Nytt anchorpane i login som blir rootpane för allt
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginBtn;

    public LoginController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    public void login(){
        String email = emailField.getText();
        String pw = passwordField.getText();
        ur.loginUser(email, pw);
        navigateToMyProfile();
    }

    @FXML
    private void navigateToMyProfile(){
        MyProfileController myProfileController = new MyProfileController(rootpane);
        rootpane.getChildren().setAll(myProfileController);
    }
}

