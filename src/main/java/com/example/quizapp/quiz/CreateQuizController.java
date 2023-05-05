package com.example.quizapp.quiz;

import com.example.quizapp.quiz.flashcard.CreateFlashCardQuizController;
import com.example.quizapp.quiz.multichoice.CreateMultiChoiceQuizController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class CreateQuizController extends AnchorPane{

    @FXML
    AnchorPane mainAnchorPane;
    @FXML
    AnchorPane createAnchorPane;

    public CreateQuizController(AnchorPane rootpane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createQuiz.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.mainAnchorPane = rootpane;
    }

    @FXML
    public void navigateToCreateMultiChoice() {
        createAnchorPane.getChildren().clear();
        createAnchorPane.getChildren().add(new CreateMultiChoiceQuizController(mainAnchorPane));
        createAnchorPane.toFront();
    }

    /**
     * This method navigates to the CreateFlashcard section of the page
     */
    @FXML
    public void navigateToCreateFlashcard() {
        createAnchorPane.getChildren().clear();
        createAnchorPane.getChildren().add(new CreateFlashCardQuizController(mainAnchorPane));
        createAnchorPane.toFront();
    }

    /**
     * This method navigates back one page
     */
    /*@FXML
    public void navigateBack() {
        createAnchorPane.getChildren().clear();
        createAnchorPane.getChildren().add(new CreateQuizController(mainAnchorPane));
        createAnchorPane.toFront();
    }

    @FXML
    public void navigateNext() {
        createAnchorPane.toBack();
        createAnchorPane.getChildren().clear();
        createAnchorPane.getChildren().add(new CreateFlashCardQuizController(mainAnchorPane));
        createAnchorPane.toFront();

    }
     */
}
