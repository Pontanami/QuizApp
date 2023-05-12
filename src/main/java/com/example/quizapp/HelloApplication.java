package com.example.quizapp;

import com.example.quizapp.firebase.IUserRepository;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.user.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;


public class HelloApplication extends Application {

    private IUserRepository userRepo;

    @Override
    public void start(Stage stage){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        NavigationStack mv = NavigationStack.getInstance();
        mv.pushView(new LoginController());
        Scene scene = new Scene(mv, screenWidth, screenHeight);

        stage.centerOnScreen();
        stage.setScene(scene);
        stage.setMinWidth(850);
        stage.setMinHeight(850);
        stage.show();
    }


    public static void main(String[] args) {        
        launch();
    }
}