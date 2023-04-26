package com.example.quizapp.controllers;


import com.example.quizapp.Quiz;
import com.example.quizapp.interfaces.IFlashcardManager;
import com.example.quizapp.model.IQuestion;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class CreateFlashCardQuiz extends AnchorPane implements IFlashcardManager {

    private AnchorPane rootpane;

    @FXML
    private AnchorPane tagPane;

    @FXML
    private AnchorPane flashcardPane;

    @FXML
    private VBox items;

    private List<CreateFlashcard> questions = new ArrayList<>();

    @FXML
    private ScrollPane flashcardScrollpane;

    @FXML
    private TextField quizName;

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
        questions.add(flashcard);

        items.getChildren().add(flashcard);
        items.setSpacing(10);

        flashcardScrollpane.setContent(items);
    }

    public void removeQuestion(CreateFlashcard flashcard){
        questions.remove(flashcard);

    }

    public void refreshView(){

        items.getChildren().clear();
        flashcardScrollpane.setContent(items);
    }

    @FXML
    public void navigateToTagPane() {
        tagPane.toFront();

        //todo remove
        createQuiz();
    }

    private void createQuiz(){
        Quiz quiz = new Quiz(quizName.getText());
        for (var item : questions) {
            var question = item.createCard();
            quiz.addQuestion(question);
        }

        var q = quiz;
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
}
