package com.example.quizapp.controllers;


import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class MainViewController extends AnchorPane {
    @FXML
    AnchorPane rootPane;

    private static MainViewController instance = null;

    public static MainViewController getInstance(AnchorPane pane) {
        if (instance == null) {
            instance = new MainViewController(pane);
        }
        return instance;
    }

    private MainViewController(AnchorPane pane){
        rootPane = pane;
    }

    @FXML
    public void navigateToLogin(){
        LoginController loginController = new LoginController();
        rootPane.getChildren().clear();
        rootPane.getChildren().add(loginController);
    }
    @FXML
    public void navigateToMyProfile(){
        MyProfileController profileController = new MyProfileController();
        rootPane.getChildren().clear();
        rootPane.getChildren().add(profileController);
    }
    @FXML
    public void navigateToRegister(){
        RegisterController registerController = new RegisterController();
        rootPane.getChildren().clear();
        rootPane.getChildren().add(registerController);
    }

}