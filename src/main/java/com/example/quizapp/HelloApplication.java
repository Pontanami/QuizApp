package com.example.quizapp;

import com.example.quizapp.model.*;
import com.example.quizapp.user.FirebaseUserRepository;
import com.example.quizapp.user.IUserRepository;
import com.example.quizapp.multiChoice.MultiChoice;
import com.example.quizapp.user.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HelloApplication extends Application {

    private IUserRepository userRepo;

    @Override
    public void start(Stage stage) {
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
        UserScore user = new UserScore();

        Scanner in = new Scanner(System.in);
        List<Flashcard> flashCards = new ArrayList<>();

        boolean running = true;
        while (running) {
            printMenu();

            String option = in.nextLine();
            System.out.println();

            switch (option) {
                case "1" -> constructFlashcard(in, flashCards);
                case "2" -> {
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

                            String userInput;
                            do {
                                System.out.println("Were you correct?");
                                System.out.println("1 - Yes");
                                System.out.println("2 - No");
                                userInput = in.nextLine();
                            } while (!userInput.equals("1") && !userInput.equals("2"));

                            boolean isCorrect = FlashcardValidator.validate(userInput);
                            int newScore = Scorer.scoreQuestion(user.getScore(), isCorrect);
                            user.setScore(newScore);

                            System.out.println("Current score: " + user.getScore());

                            if (currentFlashCard != flashCards.size() - 1) {
                                currentFlashCard++;
                            } else {
                                currentFlashCard = 0;
                            }

                            viewingQuestion = true;
                            System.out.println("Question: " + flashCards.get(currentFlashCard).getQuestion());
                        }

                        System.out.println("1 - Flip flashcard");
                        System.out.println("2 - Go to previous flashcard");
                        System.out.println("3 - Go to next flashcard");
                        if (flashCards.get(currentFlashCard).getWordHint() != null) {
                            System.out.println("4 - Show hint");
                        }
                        System.out.println("q - Exit ");
                        System.out.print("Pick an option: ");

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
                            case "4":
                                if (flashCards.get(currentFlashCard).getWordHint() != null){
                                    System.out.println("The hint is: " + flashCards.get(currentFlashCard).showHint());
                                }
                                break;

                            case "q":
                                viewingFlashCards = false;
                                break;
                        }
                        System.out.println();
                    }
                }
                case "3" -> {
                    System.out.println("Which flashcard do you want to delete?");
                    for (int i = 0; i < flashCards.size(); i++) {
                        System.out.println(i + " - " + flashCards.get(i).getQuestion());
                    }
                    int flashcard = Integer.parseInt(in.nextLine());
                    flashCards.remove(flashcard);
                }
                case "q" -> running = false;
            }
            System.out.println();
        }
    }

    private static void constructFlashcard(Scanner in, List<Flashcard> flashCards) {
        System.out.print("Enter your question: ");
        String question = in.nextLine();

        System.out.print("Enter your answer: ");
        String answer = in.nextLine();

        System.out.println("What kind of hint do you want: \n1. Half of the word hint \n2. First letter of the hint \n3. Text hint");
        String chosenOption = in.nextLine();

        IHint hint = null;
        switch (chosenOption) {
            case "1":
                hint = new HalfWordHint(answer);
                break;
            case "2":
                hint = new OneLetterHint(answer);
                break;
            case "3":
                System.out.print("Enter your hint: ");
                String hintText = in.nextLine();
                hint = new TextHint(hintText);
                break;
        }

        if(hint == null){
            flashCards.add(new Flashcard(question, answer));
        } else {
            flashCards.add(new Flashcard(question, answer, hint));
        }

    }

    private static void printMenu() {
        System.out.println("1 - Create a flashcard");
        System.out.println("2 - Show flashcards");
        System.out.println("3 - Delete flashcard");
        System.out.println("q - Exit");
        System.out.print("Pick an option: " );
    }

    private static void createMultiChoiceQuiz(){
        ArrayList<MultiChoice> questions = new ArrayList<>();
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
                        Scanner myQuestion = new Scanner(System.in);
                        System.out.println("\nWhat is your question: ");
                        String question = myQuestion.nextLine();
                        questions.add(new MultiChoice(myQuestion.nextLine()));

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
    }

}