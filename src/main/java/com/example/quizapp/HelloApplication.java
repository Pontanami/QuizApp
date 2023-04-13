package com.example.quizapp;

import com.example.quizapp.multiChoice.MultiChoice;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        /* FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show(); */
    }

    public static void main(String[] args) {
        //launch();
        ArrayList<MultiChoice> questions = new ArrayList<>();

        Scanner myQuestion = new Scanner(System.in);
        System.out.println("What is your question: ");
        String question = myQuestion.nextLine();
        questions.add(new MultiChoice(question));
    }
}