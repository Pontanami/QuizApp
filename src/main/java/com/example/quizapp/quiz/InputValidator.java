package com.example.quizapp.quiz;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.*;

public class InputValidator {

    private HashMap<Control, Boolean> validationFields = new HashMap<>();
    private Text requiredError;

    public InputValidator(){}

    public InputValidator(Text requiredError){
        this.requiredError = requiredError;
    }

    public void addValidationListener(TextField textField) {
        validationFields.put(textField, false);
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!Objects.equals(oldValue, newValue)) {
                updateTextFieldStyle(textField);

                if(requiredError != null)
                    updateRequiredErrorVisibility();
            }
        });
    }

    public boolean validFields() {
        for(var isValid : validationFields.values()){
            if(!isValid){
                return false;
            }
        }

        return true;
    }

    public void setFieldValid(TextField textField, boolean isCorrect){
        //textField.getStyleClass().add("incorrectField");
        validationFields.put(textField, isCorrect);
        updateRequiredErrorVisibility();
    }

    private void updateTextFieldStyle(TextField textField) {
        textField.getStyleClass().remove("correctField");
        textField.getStyleClass().remove("incorrectField");

        if(textField.getText() == null){
            validationFields.put(textField, true);
        } else{
            if (!textField.getText().isEmpty()) {
                textField.getStyleClass().add("correctField");
                validationFields.put(textField, true);
            } else {
                textField.getStyleClass().add("incorrectField");
                validationFields.put(textField, false);
            }
        }
    }

    private void updateRequiredErrorVisibility() {
        requiredError.setVisible(isFieldsValid());
    }

    public TextField createValidationTextField(TextField textField) {
        addValidationListener(textField);
        return textField;
    }

    public TextField createValidationTextFieldValidStart(TextField textField) {
        addValidationListener(textField);
        validationFields.put(textField, true);
        return textField;
    }


    private boolean isFieldsValid(){
        return validationFields.containsValue(false);
    }

    public boolean isValidTextField(TextField textField){
        return validationFields.get(textField);
    }
}
