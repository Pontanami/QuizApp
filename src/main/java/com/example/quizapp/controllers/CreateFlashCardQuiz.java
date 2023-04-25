package com.example.quizapp.controllers;

import com.example.quizapp.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CreateFlashCardQuiz extends AnchorPane{

    private AnchorPane rootpane;
    private VBox items = new VBox();
    @FXML
    private ScrollPane flashcardScrollpane;

    public CreateFlashCardQuiz(AnchorPane rootpane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createFlashcardQuizView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.rootpane = rootpane;
    }

    @FXML
    private void createFlashcard(){
        CreateFlashcard flashcard = new CreateFlashcard(rootpane);

        items.getChildren().add(flashcard);
        items.setSpacing(10);

        flashcardScrollpane.setContent(items);
    }
}
