package com.example.quizapp;

import com.example.quizapp.firebase.IUserRepository;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.user.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {

    private IUserRepository userRepo;

    @Override
    public void start(Stage stage){
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