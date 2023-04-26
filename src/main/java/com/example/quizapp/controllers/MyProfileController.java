package com.example.quizapp.controllers;

import com.example.quizapp.user.FirebaseUserRepository;
import com.example.quizapp.user.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class MyProfileController {
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();
    User currentUser = ur.getCurrentUser();
    @FXML
    Text nameText;
    @FXML
    Button signOutBtn;

    public MyProfileController(){
        nameText.setText(currentUser.getName());
    }

    @FXML
    private void signOut(){
        //Måste setas i databasen också?
        currentUser = null;
    }

}
