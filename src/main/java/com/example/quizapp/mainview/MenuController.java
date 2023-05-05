package com.example.quizapp.mainview;

import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.quiz.CreateQuizController;
import com.example.quizapp.quiz.QuizCollection;
import com.example.quizapp.user.LoginController;
import com.example.quizapp.user.MyProfileController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MenuController extends AnchorPane{

    @FXML
    Button ProfileBtn, QuizColBtn, CreateQuizBtn, LogoutBtn;

    NavigationStack navigationStack = NavigationStack.getInstance();

    public MenuController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @FXML
    public void NavigateToProfile(){
        navigationStack.pushView(new MyProfileController());
    }

    @FXML
    public void NavigateToQuizCollection(){
        navigationStack.pushView(new QuizCollection());
    }

    @FXML
    public void NavigateToCreateQuiz(){
        navigationStack.pushView(new CreateQuizController());
    }

    @FXML
    public void logout(){
        navigationStack.clearAll();
        navigationStack.pushView(new LoginController());
    }
}
