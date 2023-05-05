package com.example.quizapp.user;

import com.example.quizapp.NavigationStack;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.mainview.MenuController;
import com.example.quizapp.firebase.FirebaseUserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;
import java.io.IOException;

/**
 * Controller for the login screen
 * @author Alexander Persson, Felix Erngård, Nils Bengtsson Svanstedt
 */
public class LoginController extends AnchorPane {
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();

    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginBtn;
    @FXML
    Button registerBtn;

    @FXML
    AnchorPane backPane;
    @FXML
    AnchorPane parent;

    @FXML
    Text errorText;

    NavigationStack navigationStack = NavigationStack.getInstance();

    /**
     * Represents the user login view. Loads the correct fxml file using {@link FXMLLoader}
     */
    public LoginController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);

        };
        errorText.setVisible(false);
    }

    /**
     * Displays the register screen where the user can register a new account
     */
    @FXML
    private void register(){
        RegisterController rc = new RegisterController(parent);
        parent.getChildren().clear();
        parent.getChildren().add(rc);
    }

    /**
     * Navigate to the quiz collection screen
     */
    private void navigateToHome() {
        navigationStack.pushView(new HomeController());
        navigationStack.setHeader(new MenuController());
        navigationStack.removeView(this);
    }

    /**
     * Login the user if the email and password is correct, otherwise display an error message
     */
    @FXML
    public void login() {
        errorText.setVisible(false);
        String email = emailField.getText();
        String pw = passwordField.getText();
        passwordField.clear();
        emailField.clear();
        try {
            ur.loginUser(email, pw);
            navigateToHome();
        } catch (IllegalArgumentException e) {
            errorText.setText(e.getMessage());
            errorText.setVisible(true);
            emailField.setBorder(Border.stroke(javafx.scene.paint.Color.RED));
            passwordField.setBorder(Border.stroke(javafx.scene.paint.Color.RED));
        }
    }
}

