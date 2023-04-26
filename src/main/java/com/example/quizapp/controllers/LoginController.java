package com.example.quizapp.controllers;

import com.example.quizapp.user.FirebaseUserRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController extends AnchorPane{
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();
    @FXML
    AnchorPane rootpane;

    @FXML
    TextField emailField;
    @FXML
    TextField passwordField;
    @FXML
    Button loginBtn;

    //Kolla borderpane
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

