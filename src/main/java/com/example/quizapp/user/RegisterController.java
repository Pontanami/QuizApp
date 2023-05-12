package com.example.quizapp.user;

import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.quiz.InputValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

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

    NavigationStack navigationStack = NavigationStack.getInstance();

    @FXML
    private Text errorText;

    /**
     * Represents the user registration view. Loads the correct fxml file using {@link FXMLLoader}.
     */
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
        try {
            ur.createUser(name, email, pw);
            navigateToLogin();
        } catch (Exception e){
            errorText.setText(e.getMessage());
            errorText.setVisible(true);
        }
    }

    @FXML
    private void navigateToLogin(){
        navigationStack.popView();
    }
}

