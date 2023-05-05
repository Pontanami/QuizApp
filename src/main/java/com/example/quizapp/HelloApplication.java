package com.example.quizapp;

import com.example.quizapp.firebase.IUserRepository;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private IUserRepository userRepo;

    @Override
    public void start(Stage stage) throws IOException, IllegalAccessException {       
        MainViewController mv = new MainViewController();
        Scene scene = new Scene(mv, 1920, 1080);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {        
        launch();
    }
}