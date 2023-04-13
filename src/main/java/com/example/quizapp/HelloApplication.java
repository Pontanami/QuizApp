package com.example.quizapp;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class HelloApplication extends Application {

    private IUserRepository userRepo;

    @Override
    public void start(Stage stage) throws IOException {
        userRepo = new FirebaseUserRepository();
        Scanner scanner = new Scanner(System.in);
        System.out.println("ProgramTest: Write 1 to create user, 2 to login a user, 3 to get user");
        int result = scanner.nextInt();
        if(result == 1) {
            System.out.println("Write name");
            String name = scanner.next();
            System.out.println("Write email");
            String email = scanner.next();
            System.out.println("Write password");
            String password = scanner.next();
            userRepo.createUser(name, email, password);
        }
        else if (result == 2) {
            System.out.println("Write name of account");
            String name = scanner.next();
            System.out.println("Write password");
            String password = scanner.next();
            userRepo.loginUser(name, password);
            }
        else if (result == 3) {
            System.out.println("Write name of account");
            String name = scanner.next();
            System.out.println(userRepo.getUser(name));
        }


        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        //stage.setTitle("Hello!");
        //stage.setScene(scene);
        //stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}