package com.example.quizapp.mainview;

import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.firebase.FirebaseTakenQuizRepository;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.interfaces.IObservable;
import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.quiz.Quiz;
import com.example.quizapp.quiz.QuizQuery;
import com.example.quizapp.quiz.QuizThumbnail;
import com.example.quizapp.quiz.takeQuiz.TakenQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuiz;
import com.example.quizapp.user.User;
import com.example.quizapp.user.UserQuery;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.CacheHint;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.util.Comparator;

public class HomeController extends AnchorPane implements IObserver {

    @FXML
    AnchorPane parent;

    @FXML
    FlowPane recentQuizFlow;

    @FXML
    FlowPane myQuizFlow;

    FirebaseUserRepository userRepository = FirebaseUserRepository.getAuth();
    FirebaseQuizRepository quizRepository = new FirebaseQuizRepository();
    String userID = userRepository.getCurrentUser().getId();

    public HomeController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/HomeView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        double a = System.currentTimeMillis();
        populateMyRecentQuizzes();
        populateMyQuizzes();
        double b = System.currentTimeMillis();
        System.out.println(b-a + " homecontroller");
    }

    private void populateMyQuizzes(){
        double a = System.currentTimeMillis();
        QuizQuery.QuizQueryBuilder query = new QuizQuery.QuizQueryBuilder().setCreatedBy(userID);
        myQuizFlow.getChildren().clear();
        double c = System.currentTimeMillis();
        System.out.println(c-a + " populateMyQuizzes");
        for (Quiz quiz : quizRepository.getQuiz(query)) {
            QuizThumbnail quizThumbnail = new QuizThumbnail(quiz);
            myQuizFlow.getChildren().add(quizThumbnail);
        }
        double b = System.currentTimeMillis();
        System.out.println(b-c + " populateMyQuizzes");
    }

    private void populateMyRecentQuizzes(){
        double a = System.currentTimeMillis();
        Quiz quiz;
        FirebaseTakenQuizRepository takenQuizRepo = new FirebaseTakenQuizRepository();
        TakenQuery.TakenQueryBuilder query = new TakenQuery.TakenQueryBuilder().setuserId(userID).setOrder("date").setLimit(3);
        recentQuizFlow.getChildren().clear();
        double b = System.currentTimeMillis();
        System.out.println(b-a + " populateMyRecentQuizzes");

        for (TakenQuiz takenQuiz : takenQuizRepo.getTakenQuizzes(query)) {
            quiz = quizRepository.getSingleQuiz(new QuizQuery.QuizQueryBuilder().setId(takenQuiz.getQuizId()));
            QuizThumbnail quizThumbnail = new QuizThumbnail(quiz);
            recentQuizFlow.getChildren().add(quizThumbnail);
        }
        double c = System.currentTimeMillis();
        System.out.println(c-b + " populateMyRecentQuizzes");
    }

    @Override
    public void update() {
        populateMyRecentQuizzes();
        populateMyQuizzes();
    }
}
