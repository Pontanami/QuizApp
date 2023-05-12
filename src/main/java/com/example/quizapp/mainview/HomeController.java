package com.example.quizapp.mainview;

import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.quiz.Quiz;
import com.example.quizapp.quiz.QuizQuery;
import com.example.quizapp.quiz.QuizThumbnail;
import com.example.quizapp.user.User;
import com.example.quizapp.user.UserQuery;
import javafx.fxml.FXML;
import com.example.quizapp.NavigationStack;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class HomeController extends AnchorPane{

    @FXML
    AnchorPane parent;

    @FXML
    FlowPane myQuizFlow;

    FirebaseUserRepository userRepository = FirebaseUserRepository.getAuth();

    NavigationStack navigationStack = NavigationStack.getInstance();
    public void navigateBack() {
        navigationStack.popView();
    }

    public HomeController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/HomeView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        myQuizFlow.setHgap(10);
        myQuizFlow.setVgap(10);
        populateMyQuizzes();
    }

    private void populateMyQuizzes(){
        FirebaseQuizRepository quizRepository = new FirebaseQuizRepository();
        String userID = userRepository.getCurrentUser().getId();
        QuizQuery.QuizQueryBuilder query = new QuizQuery.QuizQueryBuilder().setCreatedBy(userID);
        myQuizFlow.getChildren().clear();
        for (Quiz quiz : quizRepository.getQuiz(query)) {
            QuizThumbnail quizThumbnail = new QuizThumbnail(quiz);
            quizThumbnail.setCache(true);
            quizThumbnail.setCacheShape(true);
            quizThumbnail.setCacheHint(CacheHint.SPEED);
            myQuizFlow.getChildren().add(quizThumbnail);
        }
    }
}
