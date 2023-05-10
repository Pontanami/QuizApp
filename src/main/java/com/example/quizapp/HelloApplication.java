package com.example.quizapp;

import com.example.quizapp.firebase.FirebaseTakenQuizRepository;
import com.example.quizapp.firebase.IUserRepository;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.quiz.takeQuiz.TakenQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuiz;
import com.example.quizapp.user.LoginController;
import com.google.cloud.Timestamp;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {

    private IUserRepository userRepo;
    private FirebaseTakenQuizRepository qr = new FirebaseTakenQuizRepository();

    @Override
    public void start(Stage stage){
       /* qr.uploadTakenQuiz("123", "test", 3);
        qr.uploadTakenQuiz("456", "test2", 5);
        qr.uploadTakenQuiz("789", "test3", 2);
        List quizzes = qr.getTakenQuizzes();
        List quiz = qr.getTakenQuizzesLimited();
        */

        //TakenQuery.TakenQueryBuilder query = new TakenQuery.TakenQueryBuilder().setquizId("abc").setOrder("date").setLimit(2);

        //List<TakenQuiz> quizzes= qr.getTakenQuizzes(query);
        //System.out.println(quizzes);

        NavigationStack mv = NavigationStack.getInstance();
        mv.pushView(new LoginController());
        Scene scene = new Scene(mv, 1920, 1080);

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }


    public static void main(String[] args) {        
        launch();
    }
}