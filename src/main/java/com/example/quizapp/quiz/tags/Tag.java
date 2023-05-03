package com.example.quizapp.quiz.tags;

import com.example.quizapp.quiz.Quiz;
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

    private boolean isApplied = false;

    public Tag(Subject subject, Quiz quiz) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/tag.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.subject = subject;
        this.quiz = quiz;
        tagButton.setText(String.valueOf(subject));
    }

    @FXML
    private void tagClicked(){
            System.out.println(this);
            quiz.tagSelected(subject);
    }
}
