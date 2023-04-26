package com.example.quizapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LoginController extends AnchorPane {

    //Kolla borderpane
    @FXML
    private AnchorPane loginAnchor;
    @FXML
    private TextField userName;
    @FXML
    private TextField password;
    @FXML
    private Button loginBtn;
    public LoginController(){}

    @FXML
    private void login(){
        final String username = userName.getText();
        final String pw = password.getText();
        System.out.println("Username: " + username + " Password: " + pw);
    }
}
