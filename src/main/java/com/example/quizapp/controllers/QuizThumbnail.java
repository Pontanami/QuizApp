package com.example.quizapp.controllers;

import com.example.quizapp.Quiz;
import com.example.quizapp.QuizAttempt;
import com.example.quizapp.TakeQuizController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class QuizThumbnail extends AnchorPane {

    private Quiz quiz;

    @FXML
    private ImageView quizThumbnail;

    @FXML
    private Text quizName;

    @FXML
    private AnchorPane grandParentPane;

    public QuizThumbnail(Quiz quiz, AnchorPane grandParentPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/QuizThumbnail.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.grandParentPane = grandParentPane;
        this.quiz = quiz;
        quizName.setText(quiz.getName());
    }



    @FXML
    public void navigateToQuiz() {
        TakeQuizController takeQuizController = new TakeQuizController(grandParentPane);
        takeQuizController.initializeData(new QuizAttempt(quiz));

        grandParentPane.getChildren().clear();
        grandParentPane.getChildren().add(takeQuizController);
    }
}
