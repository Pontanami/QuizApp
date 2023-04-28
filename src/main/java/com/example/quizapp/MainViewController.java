package com.example.quizapp;


import com.example.quizapp.controllers.LoginController;
import com.example.quizapp.controllers.RegisterController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MainViewController extends AnchorPane {
    @FXML
    AnchorPane rootPane;

    AnchorPane loginLoader;
    LoginController loginController = new LoginController();
    RegisterController registerController = new RegisterController();
    private static MainViewController instance;

    public static MainViewController getInstance() {
        if (instance == null) {
            instance = new MainViewController();
        }
        return instance;
    }

    public MainViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        navigateToLogin();
    }

    @FXML
    public void navigateToLogin(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(loginLoader);
    }

    @FXML
    public void navigateToRegister(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(registerController);
    }

}