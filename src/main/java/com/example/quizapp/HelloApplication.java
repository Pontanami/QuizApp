package com.example.quizapp;

import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.multiChoice.MultiChoiceModel;
import com.example.quizapp.user.FirebaseUserRepository;
import com.example.quizapp.user.IUserRepository;
import com.example.quizapp.multiChoice.MultiChoice;
import com.example.quizapp.model.Flashcard;
import com.example.quizapp.user.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelloApplication extends Application {

    private IUserRepository userRepo;

    @Override
    public void start(Stage stage) throws IOException {
        boolean running = true;
        while(running) {
            userRepo = FirebaseUserRepository.getAuth();
            Scanner scanner = new Scanner(System.in);
            System.out.println(" ");
            System.out.println("Write 1 to create user,");
            System.out.println("Write 2 to login");
            System.out.println("Write 3 to get user");
            System.out.println("Write 4 get list of all users");
            System.out.println("Write 5 to remove user");
            System.out.println("Write q to exit");
            switch (scanner.next()) {
                case "1" -> {
                    System.out.println("Write name");
                    String name = scanner.next();
                    System.out.println("Write email");
                    String email = scanner.next();
                    System.out.println("Write password");
                    String password = scanner.next();
                    userRepo.createUser(name, email, password);
                }
                case "2" -> {
                    System.out.println("Write name of account");
                    String name = scanner.next();
                    System.out.println("Write password");
                    String password = scanner.next();
                    userRepo.loginUser(name, password);
                }
                case "3" -> {
                    System.out.println("Write name of account");
                    String name = scanner.next();
                    User user = userRepo.getUser(name);
                    System.out.println("User: name=" + user.getName() + " email=" + user.getEmail() +
                            " id=" + user.getId());
                }
                case "4" -> {
                    List<String> users = new ArrayList<>();
                    int i = 1;
                    for (User user : userRepo.getUsers()) {
                        users.add("User" + i + ": name=" + user.getName() + " email=" + user.getEmail() +
                                " id=" + user.getId());
                        i++;
                    }
                    System.out.println(users);

                }
                case "5" -> {
                    System.out.println("Write name of account");
                    String name = scanner.next();
                    userRepo.removeUser(userRepo.getUser(name).getId());
                }
                case "q" -> {
                    running = false;
                    System.exit(0);
                }
            }

        }

        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //stage.setTitle("Hello!");
        //stage.setScene(scene);
        //stage.show();
    }

    public static void main(String[] args) {
        boolean running = true;
        while (running){
            Scanner in = new Scanner(System.in);
            System.out.println("1 - Create a FlashCard quiz");
            System.out.println("2 - Create a Multiple Choice quiz");
            System.out.println("3 - User operations");
            System.out.println("q - Exit");
            System.out.println("Choose an option: ");
            String choice = in.nextLine();
            switch (choice) {
                case "1" -> createFlashCardQuiz();
                case "2" -> createMultiChoiceQuiz();
                case "3" -> launch();
                case "q" -> {
                    running = false;
                    System.exit(0);
                }
                default -> System.out.println("Please choose 1, 2, or q");
            }
        }
        launch();
    }

    private static void createFlashCardQuiz() {
        Scanner in = new Scanner(System.in);
        List<Flashcard> flashCards = new ArrayList<>();
        boolean running = true;
        while (running) {
            System.out.println("1 - Create a flashcard");
            System.out.println("2 - Show flashcards");
            System.out.println("3 - Delete flashcard");
            System.out.println("q - Exit");
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
                        System.out.println("q - Exit ");
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
                                break;
                            case "3":
                                if (currentFlashCard != flashCards.size() - 1) {
                                    currentFlashCard++;
                                } else {
                                    currentFlashCard = 0;
                                }
                                break;
                            case "q":
                                viewingFlashCards = false;
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
                case "q":
                    running = false;
                    break;
            }
            System.out.println();
        }
    }

    private static void createMultiChoiceQuiz(){
        Scanner quizNameFetch = new Scanner(System.in);
        System.out.println("Enter a quiz name: ");
        String quizName = quizNameFetch.next();
        Quiz quiz = new Quiz(quizName);

        String[] options = {"Create a question", "Take a quiz", "Exit"};

        label:
        while (true){
            System.out.println("Please choose one of the options below: (1-3) \n");

            for (int i = 0; i < options.length; i++){
                System.out.println((i+1) + " - " + options[i]);
            }

            Scanner myOption = new Scanner(System.in);
            System.out.println("The option you choose is: ");
            String chosenOption = myOption.next();

            switch (chosenOption) {
                case "1":
                    while (true) {
                        quiz.addQuestion(new MultiChoice());
                        System.out.println("\nYour question has been created. Choose one of the options below: ");
                        System.out.println("1 - Create another question");
                        System.out.println("2 - Finish");
                        System.out.println("q - Exit");
                        Scanner scanner = new Scanner(System.in);
                        System.out.println("The option you choose is: ");
                        String myChoice = scanner.nextLine();

                        if (myChoice.equals("2")) {
                            System.out.print("\n");
                            break;
                        } else if (myChoice.equals("q")) {
                            System.exit(0);
                        }
                    }
                    break;
                case "2":
                    if (quiz.getQuestions().isEmpty()) {
                        System.out.println("There are no available quiz questions. You need to create them first.\n");
                    } else {
                        takeQuiz(quiz);
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
    }

    private static void takeQuiz(Quiz quiz) {
        System.out.println("You are taking the quiz " + quiz.name);
        quiz.nextQuestion().showQuestion();
        /*
        for (IQuizable question : quiz.getQuestions()){
            question.showQuestion();
        } */
    }

}