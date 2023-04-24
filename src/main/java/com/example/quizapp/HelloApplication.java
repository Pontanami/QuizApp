package com.example.quizapp;

import com.example.quizapp.user.IUserRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("multiChoice.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("QuizApp");

        String css = this.getClass().getResource("multiChoiceCSS.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
       launch();
    }

}