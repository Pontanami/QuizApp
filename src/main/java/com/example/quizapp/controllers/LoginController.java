package com.example.quizapp.controllers;

import com.example.quizapp.MainViewController;
import com.example.quizapp.user.FirebaseUserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class LoginController{
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();
    MainViewController mv = MainViewController.getInstance();
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginBtn;
    @FXML
    Button registerBtn;


    public LoginController(){
    }
    @FXML
    public void login(){
        String email = emailField.getText();
        String pw = passwordField.getText();
        ur.loginUser(email, pw);
    }


    @FXML
    private void register(){
        mv.navigateToRegister();
    }

}

