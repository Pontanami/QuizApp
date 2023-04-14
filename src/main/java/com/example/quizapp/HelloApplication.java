package com.example.quizapp;

import com.example.quizapp.multiChoice.MultiChoice;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
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
        launch();

    }

    private static void multiChoiceFlow(){
        ArrayList<MultiChoice> questions = new ArrayList<>();
        String[] options = {"Create a question", "Take a quiz", "Exit"};

        System.out.println("Welcome to QuizApp");

        label:
        while (true){
            System.out.println("Please choose one of the options below: (1-3) \n");

            for (int i = 0; i < options.length; i++){
                System.out.println((i+1) + ": " + options[i]);
            }

            Scanner myOption = new Scanner(System.in);
            System.out.println("The option you choose is: ");
            String chosenOption = myOption.next();

            switch (chosenOption) {
                case "1":
                    while (true) {
                        Scanner myQuestion = new Scanner(System.in);
                        System.out.println("\nWhat is your question: ");
                        questions.add(new MultiChoice(myQuestion.nextLine()));

                        System.out.println("\nYour question has been created. Choose one of the options below: ");
                        System.out.println("1. Create another question");
                        System.out.println("2. Finish");
                        System.out.println("3. Exit");
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("The option you choose is: ");
                        String myChoice = scanner.nextLine();

                        if (myChoice.equals("2")) {
                            System.out.print("\n");
                            break;
                        } else if (myChoice.equals("3")) {
                            System.exit(0);
                        }
                    }
                    break;
                case "2":
                    if (questions.isEmpty()) {
                        System.out.println("There are no available quiz questions. You need to create them first.\n");
                    } else {
                        for (int i = 0; i < questions.size(); i++) {
                            System.out.println("\nQuestion number " + (i + 1) + ": ");
                            questions.get(i).displayTest();
                        }
                        System.out.print("\n");
                    }
                    break;
                case "3":
                    break label;
                default:
                    System.out.println("Not a valid option.\n");
                    break;
            }
        }

        System.exit(0);
    }
}