package com.example.quizapp.controllers;

import com.example.quizapp.user.FirebaseUserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RegisterController extends AnchorPane{
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();

    @FXML
    TextField emailField;
    @FXML
    PasswordField pwField;
    @FXML
    TextField nameField;
    @FXML
    Button registerBtn;

    public RegisterController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/register.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @FXML
    private void register(){
        String name = nameField.getText();
        String pw = pwField.getText();
        String email = emailField.getText();
        ur.createUser(name, email, pw);
    }
}

