package com.example.quizapp.user;

import com.example.quizapp.quiz.QuizCollection;
import com.example.quizapp.firebase.FirebaseUserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends AnchorPane implements Initializable {
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
    AnchorPane rootpane;
    @FXML
    AnchorPane parent;

    @FXML
    Text errorText;

    public LoginController(AnchorPane parent){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parent = parent;
        errorText.setVisible(false);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void register(){
        RegisterController rc = new RegisterController(parent);
        parent.getChildren().clear();
        parent.getChildren().add(rc);
    }

    private void navigateToQuizCollection() {
        QuizCollection quizCollection = new QuizCollection(parent);
        parent.getChildren().clear();
        parent.getChildren().add(quizCollection);
    }
    @FXML
    public void login() {
        errorText.setVisible(false);
        String email = emailField.getText();
        String pw = passwordField.getText();
        passwordField.clear();
        emailField.clear();
        try {
            ur.loginUser(email, pw);
            navigateToQuizCollection();
        } catch (IllegalArgumentException e) {
            errorText.setText(e.getMessage());
            errorText.setVisible(true);
            emailField.setBorder(Border.stroke(javafx.scene.paint.Color.RED));
            passwordField.setBorder(Border.stroke(javafx.scene.paint.Color.RED));
        }
    }
}

