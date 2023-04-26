package com.example.quizapp.controllers;

import com.example.quizapp.user.FirebaseUserRepository;
import com.example.quizapp.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class MyProfileController extends AnchorPane {
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();
    User currentUser = ur.getCurrentUser();
    @FXML
    AnchorPane rootpane;
    @FXML
    Text nameText;
    @FXML
    Button signOutBtn;

    public MyProfileController(AnchorPane rootpane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/my_profile.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.rootpane = rootpane;
        nameText.setText(currentUser.getName());
    }

    @FXML
    private void signOut(){
        //Måste setas i databasen också?
        //Måste länka tillbaka till login
        navigateToLogin();
        currentUser = null;
    }

    @FXML
    private void navigateToLogin(){
        LoginController loginController = new LoginController();
        rootpane.getChildren().setAll(loginController);
    }

}
