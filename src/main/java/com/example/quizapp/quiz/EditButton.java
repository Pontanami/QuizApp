package com.example.quizapp.quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class EditButton {

    public EditButton(Quiz quiz) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editButton.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private void editQuiz(){

    }
}
