package com.example.quizapp.controllers;

import com.example.quizapp.Quiz;
import com.example.quizapp.model.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Tag extends AnchorPane {

    @FXML
    private Button tagButton;

    Quiz quiz;
    Subject subject;

    public Tag(Subject subject) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tag.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.quiz = quiz;
        tagButton.setText(String.valueOf(subject));
    }

    @FXML
    private void tagClicked(){
        quiz.addTag(subject);
    }
}
