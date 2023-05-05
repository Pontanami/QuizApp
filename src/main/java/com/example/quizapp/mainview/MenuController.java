package com.example.quizapp.mainview;

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

    @FXML
    BorderPane parent;

    public MenuController(BorderPane parent){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parent = parent;
    }

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
        MyProfileController pc = new MyProfileController(new AnchorPane());
        parent.getChildren().clear();
        parent.getChildren().add(pc);
    }

    @FXML
    public void NavigateToQuizCollection(){
        QuizCollection quizCollection = new QuizCollection(new AnchorPane());
        parent.getChildren().clear();
        parent.getChildren().add(quizCollection);
    }

    @FXML
    public void NavigateToCreateQuiz(){
        CreateQuizController createQuiz = new CreateQuizController(new AnchorPane());
        parent.getChildren().clear();
        parent.getChildren().add(createQuiz);
    }

    @FXML
    public void logout(){
        FirebaseUserRepository.getAuth().signOut();
        LoginController lc = new LoginController();
        parent.getChildren().clear();
        parent.setCenter(lc);

        //parent.getChildren().clear();
        //parent.getChildren().add(lc);
    }
}
