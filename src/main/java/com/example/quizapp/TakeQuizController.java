package com.example.quizapp;

import com.example.quizapp.model.FlashCardController;
import com.example.quizapp.model.Flashcard;
import com.example.quizapp.multiChoice.MultiChoice;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class TakeQuizController {

    @FXML
    private Label QuizName;

    @FXML
    private BorderPane QuizHolder;

    @FXML
    private ProgressBar QuizProgress;

    @FXML
    private Button QuizNext;

    @FXML
    private Button QuizPrevious;

    @FXML
    private Label QuizPoints;

    private BigDecimal progress = new BigDecimal("0.0");

    private Quiz quiz;
    private boolean isFlashCard = false;
    private boolean isMultiChoice = false;
    private int counter = 1;
    private int numberOfQuestions = 0;


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
        numberOfQuestions = quiz.getQuestions().size();
        QuizName.setText(quiz.getName());
        disablePrev();
        showQuestion();

    }

    public void showNext(){
        counter++;
        disablePrev();
        quiz.nextQuestion();
        finish();
        increaseProgress();
        showQuestion();
    }

    public void showPrevious(){
        counter--;
        disablePrev();
        finish();
        quiz.prevQuestion();
        showQuestion();
    }

    private void showQuestion() {
        AnchorPane pane = new AnchorPane();
        try {
            if (isMultiChoice) { //Maybe do a check with instanceOF?
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("multiChoice.fxml"));
                pane = fxmlLoader.load();

                MultiChoiceController controller = fxmlLoader.getController();
                controller.initializeData((MultiChoice) quiz.getCurrentQuestion());

                QuizPoints.setText("Points: " + controller.getPoints() + "/" + numberOfQuestions);
            } else if (isFlashCard){ //Maybe do a check with instanceOF?
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FlashCard.fxml"));
                pane = fxmlLoader.load();

                FlashCardController controller = fxmlLoader.getController();
                controller.initializeData((Flashcard) quiz.getCurrentQuestion());
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        QuizHolder.setCenter(pane);
    }

    public void increaseProgress(){
        int numberOfQuestions = quiz.getQuestions().size();
        double progressStep = 1.0 / numberOfQuestions;

        progress = progress.setScale(2, RoundingMode.HALF_EVEN);

        if (progress.doubleValue() < 1){
            progress = new BigDecimal(Double.toString(progress.doubleValue() + progressStep));
            QuizProgress.setProgress(progress.doubleValue());
        }
    }

    private void finish(){
        if (QuizNext.getText().equals("Finish") && counter == numberOfQuestions+1){
            Platform.exit();
        }
        else if (counter == numberOfQuestions){
            QuizNext.setText("Finish");
        }
        else {
            QuizNext.setText("Next");
        }
    }

    private void disablePrev(){
        QuizPrevious.setDisable(counter == 1);
    }
}
