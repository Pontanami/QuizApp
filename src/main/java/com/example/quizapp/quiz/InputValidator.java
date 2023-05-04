package com.example.quizapp.quiz;

import javafx.scene.control.TextField;
import java.util.Objects;

public class InputValidator {
    /**
     * Validates the user input
     * @param isCorrect User input 1 for being correct, or anything else for being false.
     * @return true if they were correct.
     */
    public static boolean validate(String isCorrect) {
        return isCorrect.equals("1");
    }

    public static void createValidationTextField(TextField textField){
        textField.textProperty().addListener((arg0, oldValue, newValue) -> {
            if(!Objects.equals(oldValue, newValue)){
                textField.getStyleClass().clear();
            }

            if(textField.getText().length() > 0){
                textField.getStyleClass().add("correctField");
            } else {
                textField.getStyleClass().add("incorrectField");
            }
        });
    }
}
