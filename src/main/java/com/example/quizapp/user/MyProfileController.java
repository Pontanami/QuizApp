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

public class MyProfileController extends AnchorPane {
    FirebaseUserRepository ur = FirebaseUserRepository.getAuth();

    User currentUser = ur.getCurrentUser();
    @FXML
    TextField nameField;
    @FXML
    Button signOutBtn;
    @FXML
    TextField emailField;
    @FXML
    TextField pwField;
    @FXML
    private Button updateBtn;

    @FXML
    private Button editBtn;
    @FXML
    private Text requiredText;
    private InputValidator validator;



    /**
     * Represents the profile page view. Loads the correct fxml file using {@link FXMLLoader}.
     */

    NavigationStack navigationStack = NavigationStack.getInstance();

    /**
     * Constructor for the profile page
     * Sets the name and email fields to the current user's name and email
     * Sets up the validator for the text fields
     */
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


    /**
     * Signs out the user by navigating to the login page
     */
    @FXML
    private void signOut(){
       navigateToLogin();
    }

    /**
     * Updates the user's profile if the fields are valid and the user has changed their details
     */
    @FXML
    private void updateProfile(){
        boolean isValid = validator.validFields();

        if (isValid && userDetailsChanged()){
            String name = nameField.getText();
            String email = emailField.getText();
            String password = pwField.getText();
            try {
                ur.patchUser(name, email, password);
            }catch (Exception e){
                requiredText.setText(e.getMessage());
                requiredText.setVisible(true);
            }
            pwField.clear();
        }
    }

    /**
     * Checks if the user has changed their details
     * @return true if the user has changed their details, false otherwise
     */
    private boolean userDetailsChanged(){
        boolean changedName = !currentUser.getName().equals(nameField.getText());
        boolean changedEmail = !currentUser.getEmail().equals(emailField.getText());
        boolean changedPw = !pwField.getText().isEmpty();
        System.out.println(changedName + " " + changedEmail + " " + changedPw);
        return changedName || changedEmail || changedPw;
    }

    /**
     * Navigates to the login page
     */
    @FXML
    private void navigateToLogin(){
        navigationStack.clearAll();
        navigationStack.pushView(new LoginController());
    }

    /**
     * Enables the user to edit their profile
     */
    @FXML
    private void enableEdit(){
        editBtn.setVisible(false);
        updateBtn.setVisible(true);
        nameField.setEditable(true);
        emailField.setEditable(true);
        pwField.setEditable(true);
    }
}
