package com.example.quizapp.user;

import com.example.quizapp.MainViewController;
import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseUserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MyProfileController extends AnchorPane implements Initializable {
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();

    User currentUser = ur.getCurrentUser();
    @FXML
    Text nameText;
    @FXML
    Button signOutBtn;
    @FXML
    TextField emailField;

    MainViewController mv;

    /**
     * Represents the profile pag view. Loads the correct fxml file using {@link FXMLLoader}.
     */

    NavigationStack navigationStack = NavigationStack.getInstance();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void signOut(){
       navigateToLogin();
    }

    @FXML
    private void navigateToLogin(){
        navigationStack.clearAll();
        navigationStack.pushView(new LoginController());
    }
}
