package com.example.quizapp;


import com.example.quizapp.quiz.QuizCollection;
import com.example.quizapp.quiz.CreateQuizController;
import com.example.quizapp.user.LoginController;
import com.example.quizapp.user.MyProfileController;
import com.example.quizapp.user.RegisterController;
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

    /**
     * Represents the main page view. Loads the correct fxml file using {@link FXMLLoader}
     */
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
    /**
     * Navigate to the quiz creation view
     * @see CreateQuizController
     */
    @FXML
    public void navigateToCreateQuiz(){
        CreateQuizController createQuizController = new CreateQuizController(rootPane);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(createQuizController);
    }
    /**
     * Navigate to the user login view
     * @see LoginController
     */
    @FXML
    public void navigateToLogin(){
        rootPane.getChildren().clear();
        rootPane.getChildren().add(loginController);
    }
    /**
     * Navigate to the profile page view
     * @see MyProfileController
     */
    @FXML
    public void navigateToMyProfile(){
        MyProfileController profileController = new MyProfileController(rootPane);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(profileController);
    }
    /**
     * Navigate to the user registration view
     * @see RegisterController
     */
    @FXML
    public void navigateToRegister(){
        RegisterController registerController = new RegisterController(rootPane);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(registerController);
    }

    /**
     * Navigate to the quiz collection view
     * @see QuizCollection
     */
    @FXML
    public void navigateToQuizCollection() {
        QuizCollection quizCollection = new QuizCollection(rootPane);
        rootPane.getChildren().clear();
        rootPane.getChildren().add(quizCollection);
    }

}