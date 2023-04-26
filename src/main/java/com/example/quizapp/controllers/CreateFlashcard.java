package com.example.quizapp.controllers;

import com.example.quizapp.interfaces.IFlashcardManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CreateFlashcard extends AnchorPane{

    private IFlashcardManager quiz;

    public CreateFlashcard(IFlashcardManager quiz) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createFlashcard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.quiz = quiz;
    }


    @FXML
    public void removeFlashcard(){
        quiz.removeQuestion(this);
    }
}
