package com.example.quizapp;

import com.example.quizapp.firebase.IUserRepository;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.user.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private IUserRepository userRepo;

    @Override
    public void start(Stage stage){
        LoginController mv = new LoginController();
        Scene scene = new Scene(mv, 1920, 1080);

        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {        
        launch();
    }
}