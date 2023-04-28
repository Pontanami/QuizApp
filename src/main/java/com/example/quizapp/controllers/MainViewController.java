package com.example.quizapp.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

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

    public static MainViewController getInstance() {
        return instance;
    }

    private MainViewController(AnchorPane pane){
        rootPane = pane;
    }

    @FXML
    public void navigateToLogin(){
        AnchorPane ap;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        try {
            ap = fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        rootPane.getChildren().clear();
        rootPane.getChildren().add(ap);
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