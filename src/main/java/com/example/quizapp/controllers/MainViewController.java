package com.example.quizapp.controllers;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController extends AnchorPane implements Initializable {
    @FXML
    AnchorPane rootPane;
    LoginController loginController;

    public MainViewController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/mainView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginController = new LoginController(rootPane);
        navigateToLogin();
    }

    @FXML
    public void navigateToCreateQuiz(){
        CreateQuiz createQuizController = new CreateQuiz(rootPane);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(createQuizController);
    }

    @FXML
    public void navigateToLogin(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(loginController);
    }
    @FXML
    public void navigateToMyProfile(){
        MyProfileController profileController = new MyProfileController(rootPane);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(profileController);
    }
    @FXML
    public void navigateToRegister(){
        RegisterController registerController = new RegisterController(rootPane);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(registerController);
    }

    @FXML
    public void navigateToQuizCollection() {
        QuizCollection quizCollection = new QuizCollection(rootPane);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(quizCollection);
    }

}