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
        List<Flashcard> flashCards = new ArrayList<>();
        boolean running = true;
        while (running) {
            System.out.println("1 - Create a flashcard");
            System.out.println("2 - Show flashcards");
            System.out.println("3 - Delete flashcard");
            System.out.println("Q - Exit");
            System.out.print("Pick an option: " );

            String option = in.nextLine();
            System.out.println();

            switch (option) {
                case "1":
                    System.out.print("Enter your question: ");
                    String question = in.nextLine();

                    System.out.print("Enter your answer: ");
                    String answer = in.nextLine();


                    flashCards.add(new Flashcard(question, answer));
                    break;
                case "2":
                    if (flashCards.size() == 0) {
                        System.out.println("No flashcards added.");
                        break;
                    }
                    boolean viewingFlashCards = true;
                    boolean viewingQuestion = true;
                    int currentFlashCard = 0;
                    while (viewingFlashCards) {
                        if (viewingQuestion) {
                            System.out.println("Question: " + flashCards.get(currentFlashCard).getQuestion());
                        } else {
                            System.out.println("Answer: " + flashCards.get(currentFlashCard).getAnswer());
                        }

                        System.out.println("1 - Flip flashcard");
                        System.out.println("2 - Go to previous flashcard");
                        System.out.println("3 - Go to next flashcard");
                        System.out.println("Q - Exit ");
                        System.out.print("Pick an option: " );

                        option = in.nextLine();
                        System.out.println();


                        switch (option) {
                            case "1":
                                viewingQuestion = !viewingQuestion;
                                break;
                            case "2":
                                if (currentFlashCard != 0) {
                                    currentFlashCard--;
                                } else {
                                    currentFlashCard = flashCards.size() - 1;
                                }
                                viewingQuestion = true;
                                break;
                            case "3":
                                if (currentFlashCard != flashCards.size() - 1) {
                                    currentFlashCard++;
                                } else {
                                    currentFlashCard = 0;
                                }
                                viewingQuestion = true;
                                break;
                            case "Q":
                                viewingFlashCards = false;
                                break;
                            default:
                                System.out.println("That is not a valid option");
                                break;
                        }
                        System.out.println();

                    }
                    break;
                case "3":
                    System.out.println("Which flashcard do you want to delete?");
                    for (int i = 0; i < flashCards.size(); i++) {
                    System.out.println(i + " - " + flashCards.get(i).getQuestion());
                    }
                    int flashcard = Integer.parseInt(in.nextLine());
                    flashCards.remove(flashcard);
                    break;
                case "Q":
                    running = false;
                    break;
                default:
                    System.out.println("That is not a valid option");
                    break;
            }
            System.out.println();
        }

        launch();
    }

}