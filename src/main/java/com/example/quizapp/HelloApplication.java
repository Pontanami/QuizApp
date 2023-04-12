package com.example.quizapp;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ExecutionException, InterruptedException {
        IUserRepository userRepository = new FirebaseUserRepository();
        Scanner scanner = new Scanner(System.in);
        System.out.println("ProgramTest: Write 1 to create user");
        if(scanner.nextInt() == 1) {
            System.out.println("Write name");
            //wait for input
            String name = scanner.next();
            System.out.println("Write email");
            //wait for input
            String email = scanner.next();
            System.out.println("Write password");
            //wait for input
            String password = scanner.next();
            userRepository.createUser(name, email, password);

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