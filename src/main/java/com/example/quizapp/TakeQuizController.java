package com.example.quizapp;

import com.example.quizapp.interfaces.IAnswerable;
import com.example.quizapp.model.FlashCardController;
import com.example.quizapp.model.Flashcard;
import com.example.quizapp.multiChoice.MultiChoice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class TakeQuizController{

    @FXML private Label QuizName;
    @FXML private BorderPane QuizHolder;
    @FXML private ProgressBar quizProgress;
    @FXML private Button quizNext;
    @FXML private Button answerButton;
    @FXML private Button hintButton;
    @FXML private Button quizPrevious;


    private IAnswerable specificController;

    private Quiz quiz;
    private boolean isFlashCard = false;
    private boolean isMultiChoice = false;
    BigDecimal progress = new BigDecimal("0.0");

    public void setAsFlashCardQuiz(){
        isFlashCard = true;
        isMultiChoice = false;
    }
    public void setAsMultiChoiceQuiz(){
        isMultiChoice = true;
        isFlashCard = false;
    }

    public void initializeData(Quiz quiz){
        this.quiz = quiz;
        QuizName.setText(quiz.getName());
        quizNext.setDisable(true);
        quizPrevious.setDisable(true);
        showQuestion();
    }

    public void showNext(){
        quiz.nextQuestion();
        increaseProgress();
        showQuestion();
        quizNext.setDisable(true);
        answerButton.setDisable(false);
        hintButton.setDisable(false);
        quizPrevious.setDisable(false);
    }

    public void showPrevious(){
        quiz.prevQuestion();
        showQuestion();
    }

    public void showHint(){
        specificController.showHint();
        hintButton.setDisable(true);
    }

    public void showAnswer(){
        specificController.revealAnswer();
        quizNext.setDisable(false);
        answerButton.setDisable(true);
        hintButton.setDisable(true);
    }

    private void showQuestion() {
        AnchorPane pane = new AnchorPane();
        try {
            if (isMultiChoice) { //Maybe do a check with instanceOF?
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("multiChoice.fxml"));
                pane = fxmlLoader.load();

                MultiChoiceController controller = fxmlLoader.getController();
                controller.initializeData((MultiChoice) quiz.getCurrentQuestion());
                specificController = controller;
            } else if (isFlashCard){ //Maybe do a check with instanceOF?
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FlashCard.fxml"));
                pane = fxmlLoader.load();

                FlashCardController controller = fxmlLoader.getController();
                controller.initializeData((Flashcard) quiz.getCurrentQuestion());
                specificController = controller;
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        QuizHolder.setCenter(pane);
    }

    private void increaseProgress(){
        double n = quiz.getQuestions().size();
        double step = 1.0 / n;

        progress = progress.setScale(2, RoundingMode.HALF_EVEN);

        if (progress.doubleValue() < 1){
            progress = new BigDecimal(Double.toString(progress.doubleValue() + step));
            quizProgress.setProgress(progress.doubleValue());
        }
    }


}
