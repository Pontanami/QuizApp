package com.example.quizapp.controllers;

import com.example.quizapp.user.FirebaseUserRepository;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends AnchorPane implements Initializable {
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();

    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginBtn;
    @FXML
    Button registerBtn;

    @FXML
    AnchorPane rootpane;
    MainViewController mv;

    public LoginController(){
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mv = MainViewController.getInstance(rootpane);
    }

    @FXML
    public void login(){
        String email = emailField.getText();
        String pw = passwordField.getText();
        ur.loginUser(email, pw);
        mv.navigateToMyProfile();
    }


    @FXML
    private void register(){
        mv.navigateToRegister();
    }


}

