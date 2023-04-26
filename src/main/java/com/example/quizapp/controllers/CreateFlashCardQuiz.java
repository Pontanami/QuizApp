package com.example.quizapp.controllers;

import com.example.quizapp.interfaces.IFlashcardManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CreateFlashCardQuiz extends AnchorPane implements IFlashcardManager {

    private AnchorPane rootpane;

    @FXML
    private AnchorPane tagPane;

    @FXML
    private VBox items;

    @FXML
    private ScrollPane flashcardScrollpane;

    public CreateFlashCardQuiz(AnchorPane rootpane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createFlashcardQuiz.fxml"));
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
        CreateFlashcard flashcard = new CreateFlashcard(this);

        items.getChildren().add(flashcard);
        items.setSpacing(10);

        flashcardScrollpane.setContent(items);
    }

    public void removeQuestion(Pane flashcard){

        items.getChildren().remove(flashcard);
        items.setSpacing(10);

        flashcardScrollpane.setContent(items);
    }

    @FXML
    public void navigateToTagPane() {
        tagPane.toFront();
    }
}
