package com.example.quizapp.controllers;

import com.example.quizapp.user.FirebaseUserRepository;
import com.example.quizapp.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class MyProfileController extends AnchorPane {
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();

    User currentUser = ur.getCurrentUser();
    @FXML
    Text nameText;
    @FXML
    Button signOutBtn;
    @FXML
    TextField emailField;

    public MyProfileController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/my_profile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        nameText.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());
    }

    @FXML
    private void signOut(){

    }
}
