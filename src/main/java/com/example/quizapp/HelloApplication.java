package com.example.quizapp;

import com.example.quizapp.controllers.LoginController;
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
/*        boolean running = true;
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
                    UserQuery.UserQueryBuilder userQ = new UserQuery.UserQueryBuilder().setName(name);
                    List<User> users = userRepo.getUsers(userQ.build());
                    for (User user : users)
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
                    System.out.println("Write id of account");
                    String id = scanner.next();
                    userRepo.removeUser(id);
                }
                case "q" -> {
                    running = false;
                    System.exit(0);
                }
            }

        }*/

        MainViewController mv = new MainViewController();
        Scene scene = new Scene(mv, 1920, 1080);
        stage.setTitle("QuizApp");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        /*boolean running = true;
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
        }*/
        launch();
    }

    private static void createFlashCardQuiz() {
        Scanner quizNameFetch = new Scanner(System.in);
        System.out.println("Enter a quiz name: ");
        String quizName = quizNameFetch.nextLine();
        Quiz quiz = new Quiz(quizName);

        System.out.println("How many subject does the quiz have: ");

        String tagNumber = quizNameFetch.nextLine();
        for (int i = 0; i < Integer.parseInt(tagNumber); i++){
            System.out.println("Please add a tag here: ");
            quiz.addTag(Quiz.Subjects.valueOf(quizNameFetch.next().toUpperCase(Locale.ROOT)));
        }

        Scanner in = new Scanner(System.in);
        boolean running = true;
        while (running) {
            printMenu();

            String option = in.nextLine();
            System.out.println();

            switch (option) {
                case "1" ->{
                    constructFlashcard(in, quiz);
                }

                case "2" -> {
                    if (quiz.getQuestions().size() == 0) {  //Changed from flashcards list
                        System.out.println("No flashcards added.");
                        break;
                    }
                    boolean viewingFlashCards = true;
                    boolean viewingQuestion = true;
                    while (viewingFlashCards) {

                        if (viewingQuestion) {
                            System.out.println("Question: " + quiz.getCurrentQuestion().getQuestion());
                        } else {
                            System.out.println("Question: " + quiz.getCurrentQuestion().getQuestion());
                            System.out.println("Answer: " + quiz.getCurrentQuestion().getAnswer());

                            String userInput;
                            do {
                                System.out.println("Were you correct?");
                                System.out.println("1 - Yes");
                                System.out.println("2 - No");
                                userInput = in.nextLine();
                            } while (!userInput.equals("1") && !userInput.equals("2"));

                            boolean isCorrect = FlashcardValidator.validate(userInput);
                            if(isCorrect)
                                quiz.addPoint();

                            System.out.println("Current score: " + quiz.getPoints());

                            viewingQuestion = true;
                            System.out.println("Question: " + quiz.getCurrentQuestion().getQuestion());
                            System.out.println("Answer: " + quiz.getCurrentQuestion().getAnswer());
                        }

                        System.out.println("1 - Flip flashcard");
                        System.out.println("2 - Go to previous flashcard");
                        System.out.println("3 - Go to next flashcard");
                        System.out.println("4 - Show hint");
                        System.out.println("q - Exit ");
                        System.out.print("Pick an option: ");

                        option = in.nextLine();
                        System.out.println();

                        switch (option) {
                            case "1":
                                viewingQuestion = !viewingQuestion;
                                break;
                            case "2":
                                quiz.prevQuestion();
                                break;
                            case "3":
                                quiz.nextQuestion();
                                break;
                            case "4":
                                System.out.println("The hint is: " + quiz.getCurrentQuestion().showHint());
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
                    for (int i = 0; i < quiz.getQuestions().size(); i++) {
                        System.out.println(i + " - " + quiz.getQuestions().get(i));
                    }
                }
                case "q" -> running = false;
            }

            }
            System.out.println();
    }

    private static void constructFlashcard(Scanner in, Quiz quiz) {
        System.out.print("Enter your question: ");
        String question = in.nextLine();

        System.out.print("Enter your answer: ");
        String answer = in.nextLine();

        System.out.println("What kind of hint do you want: \n1. Half of the word hint \n2. First letter of the hint \n3. Text hint. \n4. No hint");
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
            quiz.addQuestion(new Flashcard(question, answer));
        } else {
            quiz.addQuestion(new Flashcard(question, answer, hint));
        }
            System.out.println();

    }

    private static void printMenu() {
        System.out.println("1 - Create a flashcard");
        System.out.println("2 - Show flashcards");
        System.out.println("3 - Delete flashcard");
        System.out.println("q - Exit");
        System.out.print("Pick an option: " );
    }

    private static void createMultiChoiceQuiz(){
        Scanner quizNameFetch = new Scanner(System.in);
        System.out.println("Enter a quiz name: ");
        String quizName = quizNameFetch.nextLine();
        Quiz quiz = new Quiz(quizName);

        System.out.println("How many subject does the quiz have: ");
        String tagNumber = quizNameFetch.nextLine();
        for (int i = 0; i < Integer.parseInt(tagNumber); i++){
            System.out.println("Please add a tag here: ");
            quiz.addTag(Quiz.Subjects.valueOf(quizNameFetch.next().toUpperCase(Locale.ROOT)));
        }

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
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("What is your question :");
                        String question = scanner.nextLine();  // Read user input

                        List<String> answers = new ArrayList<>();
                        for (int i = 0; i < 4; i++){
                            while (true){
                                System.out.print("Choice number " + (i+1) + ": ");
                                answers.add(i,scanner.nextLine());  // Read user input

                                if (!answers.get(i).isEmpty()){
                                    break;
                                }
                            }
                        }

                        System.out.println("\n" + "Which one is the correct answer (1-4): ");
                        String input = scanner.nextLine();
                        String answer = answers.get(Integer.parseInt(input)-1);

                        MultiChoice model = new MultiChoice(question, answer, answers, new EliminateChoiceHint(answers, answer));
                        quiz.addQuestion(model);

                        System.out.println("\nYour question has been created. Choose one of the options below: ");
                        System.out.println("1 - Create another question");
                        System.out.println("2 - Finish");
                        System.out.println("q - Exit");

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
                    Scanner scanner = new Scanner(System.in);
                    if (quiz.getQuestions().isEmpty()) {
                        System.out.println("There are no available quiz questions. You need to create them first.\n");
                    } else {

                        for (IQuizable<?> ques : quiz.getQuestions()) {
                            MultiChoice currentQues = (MultiChoice) ques;
                            System.out.println(quiz.getCurrentQuestion().getQuestion());
                            for (int j = 0; j < 4; j++) {

                                System.out.println("Choice " + (j + 1) + ": " + currentQues.getChoices().get(j));
                            }

                            System.out.println("\n" + "Which one is the correct answer (1-4): ");
                            System.out.println("\n" + "Get hint (5)");
                            String correct = scanner.nextLine();
                            String choice;
                            if(correct.equals("5")){
                                var updatedChoices = currentQues.showHint();

                                for (int j = 0; j < updatedChoices.size(); j++) {
                                    System.out.println("Choice " + (j + 1) + ": " + updatedChoices.get(j));
                                }

                                System.out.println("\n" + "Which one is the correct answer (1-2): ");
                                correct = scanner.nextLine();
                                choice = updatedChoices.get(Integer.parseInt(correct)-1);
                            } else {
                                choice = currentQues.getChoices().get(Integer.parseInt(correct)-1);
                            }

                            if (choice.equals(currentQues.getAnswer())) {
                                System.out.println("That's correct");
                            } else {
                                System.out.println("Sorry, the correct answer is: " + currentQues.getAnswer());
                            }
                            quiz.nextQuestion();
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

    private void createCorrectAnswer(){
        while (true){
            Scanner correctAns = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Specify the correct answer (1-4): ");
            String correct = correctAns.nextLine();  // Read user input

            try {
                //model.setCorrectAnswer(correct);
                break;

            }catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}