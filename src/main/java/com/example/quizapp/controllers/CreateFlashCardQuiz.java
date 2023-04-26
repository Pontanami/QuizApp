package com.example.quizapp.controllers;


import com.example.quizapp.interfaces.IFlashcardManager;
import com.example.quizapp.model.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class CreateFlashCardQuiz extends AnchorPane implements IFlashcardManager, Initializable {

    private AnchorPane rootpane;

    @FXML
    private AnchorPane tagPane;

    @FXML
    private AnchorPane flashcardPane;

    @FXML
    private VBox items;

    @FXML
    private ScrollPane flashcardScrollpane;

    @FXML
    private FlowPane tagBox;

    @FXML
    private VBox appliedTagBox;

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

    @FXML
    private void navigateToMenu(){
        rootpane.getChildren().clear();
        rootpane.getChildren().add(new CreateQuiz());
    }

    @FXML
    private void navigateToFlashcard(){
        flashcardPane.toFront();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tagBox.setHgap(10);
        tagBox.setVgap(10);

        for (Subject subject : Subject.values()){
            tagBox.getChildren().add(new Tag(subject));
        }
    }
}
