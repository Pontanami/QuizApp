package com.example.quizapp;

import com.example.quizapp.model.Flashcard;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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




        List<Flashcard> flashcards = new ArrayList<>();
        boolean isRunning = true;

        while (isRunning){
            System.out.println("1 - Create flashcards");
            System.out.println("2 - Show flashcards");
            System.out.println("3 - Delete flashcard");
            System.out.println("Q - Exit");

            String alternative = in.nextLine();
            switch (alternative.toLowerCase()){
                case "1":
                    System.out.print("Create your question: ");
                    String question = in.nextLine();

                    System.out.print("And the answer: ");
                    String answer = in.nextLine();

                    clearConsole();

                    flashcards.add(new Flashcard(question, answer));
                    break;

                case "2":
                    for (Flashcard flashcard : flashcards){
                        System.out.println(flashcard.getQuestion());

                        System.out.println("Enter anything to see the answer");
                        in.nextLine();

                        System.out.println(flashcard.getAnswer());
                    }
                    break;
                case "3":
                    for (int i = 0; i < flashcards.size(); i++){
                        System.out.println(i + " - " + flashcards.get(i).getQuestion());
                    }

                    System.out.print("Enter the index you want to remove: ");
                    String removeIndex = in.nextLine();

                    try{
                        flashcards.remove(Integer.parseInt(removeIndex));
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "q":
                    isRunning = false;
                    break;
            }
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