package com.example.quizapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.auth.FirebaseAuth;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}