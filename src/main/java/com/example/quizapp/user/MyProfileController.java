package com.example.quizapp.user;

import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.quiz.InputValidator;
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
    TextField nameField;
    @FXML
    Button signOutBtn;
    @FXML
    TextField emailField;

    @FXML
    private Button updateBtn;

    @FXML
    private Button editBtn;
    @FXML
    private Text requiredText;
    private InputValidator validator;



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
        nameField.setText(currentUser.getName());
        emailField.setText(currentUser.getEmail());

        validator = new InputValidator(requiredText);
        validator.createValidationTextFieldValidStart(nameField);
        validator.createValidationTextFieldValidStart(emailField);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @FXML
    private void signOut(){
       navigateToLogin();
    }

    @FXML
    private void updateProfile(){
        boolean isValid = validator.validFields();

        if (isValid && userDetailsChanged()){
            String name = nameField.getText();
            String email = emailField.getText();
            ur.patchUser(name, email);
        }
    }

    private boolean userDetailsChanged(){
        boolean changedName = !currentUser.getName().equals(nameField.getText());
        boolean changedEmail = !currentUser.getEmail().equals(emailField.getText());
        return changedName || changedEmail;
    }

    @FXML
    private void navigateToLogin(){
        navigationStack.clearAll();
        navigationStack.pushView(new LoginController());
    }

    @FXML
    private void enableEdit(){
        editBtn.setVisible(false);
        updateBtn.setVisible(true);
        nameField.setEditable(true);
        emailField.setEditable(true);
    }
}
