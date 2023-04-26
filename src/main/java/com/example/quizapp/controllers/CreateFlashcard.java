package com.example.quizapp.controllers;

import com.example.quizapp.interfaces.ICreateQuestion;
import com.example.quizapp.interfaces.IQuizManager;
import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.model.Flashcard;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CreateFlashcard extends AnchorPane implements ICreateQuestion<String> {

    private IQuizManager questionManager;
    @FXML private TextField frontSide;
    @FXML private TextField backSide;
    private Flashcard flashcard;

    public CreateFlashcard(IQuizManager questionManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createFlashcard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.questionManager = questionManager;
        flashcard = new Flashcard(frontSide.getText(), backSide.getText());

        frontSide.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    flashcard.setQuestion(frontSide.getText());
                }

            }
        });

        backSide.focusedProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
            {
                if (!newPropertyValue)
                {
                    flashcard.setAnswer(backSide.getText());
                }

            }
        });
    }



    public IQuizable<String> createQuestion(){
        flashcard = new Flashcard(frontSide.getText(), backSide.getText());
        return flashcard;
    }

    public IQuizable<String> getQuestion(){
        return flashcard;
    }

    @FXML
    public void removeQuestion(){
        questionManager.removeQuestion(flashcard);
    }
}
