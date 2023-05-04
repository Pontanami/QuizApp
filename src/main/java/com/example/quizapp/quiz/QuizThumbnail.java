package com.example.quizapp.quiz;

import com.example.quizapp.quiz.takeQuiz.QuizAttempt;
import com.example.quizapp.quiz.takeQuiz.TakeQuizController;
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

    /**
     * Represents one single thumbnail for one single {@link Quiz} instance.
     * @param quiz The {@link Quiz} to create a thumbnail for.
     * @param grandParentPane The {@link AnchorPane} to populate and navigate from.
     */
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


    /**
     * navigate to the clicked quiz
     */
    @FXML
    public void navigateToQuiz() {
        TakeQuizController takeQuizController = new TakeQuizController(grandParentPane);
        takeQuizController.initializeData(new QuizAttempt(quiz));

        grandParentPane.getChildren().clear();
        grandParentPane.getChildren().add(takeQuizController);
    }
}
