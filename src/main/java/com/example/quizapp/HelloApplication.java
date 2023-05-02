package com.example.quizapp;

import com.example.quizapp.controllers.LoginController;
import com.example.quizapp.controllers.MainViewController;
import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.multiChoice.EliminateChoiceHint;
import com.example.quizapp.multiChoice.MultiChoice;
import com.example.quizapp.model.*;
import com.example.quizapp.user.IUserRepository;
import com.example.quizapp.model.Flashcard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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