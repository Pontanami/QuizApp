package com.example.quizapp.controllers;


import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.model.Subject;
import com.example.quizapp.Quiz;
import com.example.quizapp.interfaces.ICreateQuestion;
import com.example.quizapp.interfaces.IQuizManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateFlashCardQuiz extends AnchorPane implements IQuizManager<CreateFlashcard>, Initializable, IObserver {
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
    private FlowPane tagBox;

    @FXML
    private VBox appliedTagBox;

    private TextField quizName;

    private Quiz quiz = new Quiz();

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

        quiz.subscribe(this);
    }

    //todo Move to quiz model
    @FXML
    public void addQuestion(){
        CreateFlashcard flashcard = new CreateFlashcard(this);
        questions.add(flashcard);
        //update(); remove
    }

    public void removeQuestion(ICreateQuestion<CreateFlashcard> flashcard){
        //quiz.removeQuestion(flashcard);

        questions.remove(flashcard);
        update();
    }

    @Override
    public void update(){
        items.getChildren().clear();
        items.getChildren().addAll(questions);
        flashcardScrollpane.setContent(items);

        appliedTagBox.getChildren().clear();
        for (Subject subject : quiz.getTags()){
            Tag tag = new Tag(subject, quiz);
            appliedTagBox.getChildren().add(tag);
        }

        createTagsInPane();
    }

    @FXML
    public void navigateToTagPane() {
        tagPane.toFront();

        //todo remove
        createQuiz();
    }

    private void createQuiz(){
        Quiz quiz = new Quiz();
        for (var item : questions) {
            var question = item.createQuestion();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tagBox.setHgap(10);
        tagBox.setVgap(10);

        appliedTagBox.setSpacing(10);

        createTagsInPane();
    }

    private void createTagsInPane() {
        tagBox.getChildren().clear();
        for (Subject subject : Subject.values()){
            if (!quiz.getTags().contains(subject))
            tagBox.getChildren().add(new Tag(subject, quiz));
        }
    }

}
