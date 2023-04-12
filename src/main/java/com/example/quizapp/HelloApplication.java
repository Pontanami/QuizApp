package com.example.quizapp;

import com.example.quizapp.model.Flashcard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("1 - Create flashcards");
        System.out.println("2 - Show flashcards");
        System.out.println("3 - Delete flashcard");
        System.out.println("Q - Exit");


        System.out.print("Create your question: ");
        String question = in.nextLine();

        System.out.print("And the answer: ");
        String answer = in.nextLine();

        clearConsole();

        Flashcard flashcard = new Flashcard(question, answer);

        System.out.println(flashcard.getQuestion());

        System.out.println("Press S to see the answer");
        String choice = in.nextLine();

        if (choice.equalsIgnoreCase("s")){
            System.out.println(flashcard.getAnswer());
        }


        launch();
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("33[H33[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}