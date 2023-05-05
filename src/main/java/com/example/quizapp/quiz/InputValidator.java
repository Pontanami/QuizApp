package com.example.quizapp.quiz;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.util.*;

public class InputValidator {

    public HashMap<Control, Boolean> validationFields = new HashMap<>();
    private Text requiredError;

    /**
     * Validates the user input
     * @param isCorrect User input 1 for being correct, or anything else for being false.
     * @return true if they were correct.
     */
    public static boolean validate(String isCorrect) {
        return isCorrect.equals("1");
    }

    public InputValidator(){}

    public InputValidator(Text requiredError){
        this.requiredError = requiredError;
    }

    public void createValidationHeader(TextField textField){
        textField.textProperty().addListener((arg0, oldValue, newValue) -> {
            if(!Objects.equals(oldValue, newValue)){
                textField.getStyleClass().clear();

                if(textField.getText() == null){
                    textField.getStyleClass().add("correctField");
                    validationFields.put(textField, true);
                } else {
                    if(!textField.getText().isEmpty()){
                        textField.getStyleClass().add("correctField");
                        validationFields.put(textField, true);
                    } else{
                        textField.getStyleClass().add("incorrectField");
                        validationFields.put(textField, false);
                    }
                }
            }
        });
    }

    public TextField createValidationTextField(TextField textField){
        validationFields.put(textField, false);

        textField.textProperty().addListener((arg0, oldValue, newValue) -> {
            if(!Objects.equals(oldValue, newValue)){
                textField.getStyleClass().clear();

                if(textField.getText() == null){
                    textField.getStyleClass().add("correctField");
                    validationFields.put(textField, true);
                    requiredError.setVisible(false);
                } else {
                    if(!textField.getText().isEmpty()){
                        textField.getStyleClass().add("correctField");
                        validationFields.put(textField, true);
                    } else{
                        textField.getStyleClass().add("incorrectField");
                        validationFields.put(textField, false);
                    }
                }

                if(isFieldsValid()){
                    requiredError.setVisible(true);
                } else{
                    requiredError.setVisible(false);
                }
            }
        });

        return textField;
    }

    private boolean isFieldsValid(){
        return validationFields.containsValue(false);
    }

    public boolean isValidTextField(TextField textField){
        return validationFields.get(textField);
    }
}
