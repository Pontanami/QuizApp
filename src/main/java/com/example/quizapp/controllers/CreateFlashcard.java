package com.example.quizapp.controllers;

import com.example.quizapp.interfaces.IFlashcardManager;
import com.example.quizapp.model.Flashcard;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CreateFlashcard extends AnchorPane{

    private IFlashcardManager quiz;
    @FXML private TextField frontSide;
    @FXML private TextField backSide;

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
        //Flashcard flashcard = new Flashcard();
        /*frontSide.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    System.out.println("Save flashcard");

                    Flashcard flashcard = new Flashcard();

                }

            }
        });
         */

    public Flashcard createCard(){
        Flashcard flashcard = new Flashcard(frontSide.getText(), backSide.getText());
        return flashcard;
    }

    @FXML
    public void removeFlashcard(){
        quiz.removeQuestion(this);
    }
}
