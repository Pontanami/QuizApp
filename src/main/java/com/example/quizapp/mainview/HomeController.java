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
import java.util.List;

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
        populateMyRecentQuizzes();
        populateMyQuizzes();
    }

    private void populateMyQuizzes(){
        QuizQuery.QuizQueryBuilder query = new QuizQuery.QuizQueryBuilder().setCreatedBy(userID);
        myQuizFlow.getChildren().clear();
        for (Quiz quiz : quizRepository.getQuiz(query)) {
            QuizThumbnail quizThumbnail = new QuizThumbnail(quiz);
            myQuizFlow.getChildren().add(quizThumbnail);
        }
    }

    private void populateMyRecentQuizzes(){
        Quiz quiz;
        FirebaseTakenQuizRepository takenQuizRepo = new FirebaseTakenQuizRepository();
        TakenQuery.TakenQueryBuilder query = new TakenQuery.TakenQueryBuilder().setuserId(userID).setOrder("date").setLimit(3);
        QuizQuery.QuizQueryBuilder emptyQuery = new QuizQuery.QuizQueryBuilder();
        List<Quiz> allQuizzes = quizRepository.getQuiz(emptyQuery);
        recentQuizFlow.getChildren().clear();
        for (TakenQuiz takenQuiz : takenQuizRepo.getTakenQuizzes(query)) {
            quiz = allQuizzes.stream().filter(q -> q.getId().equals(takenQuiz.getQuizId())).findFirst().orElse(null);
            QuizThumbnail quizThumbnail = new QuizThumbnail(quiz);
            recentQuizFlow.getChildren().add(quizThumbnail);
        }
    }

    @Override
    public void update() {
        populateMyRecentQuizzes();
        populateMyQuizzes();
    }
}
