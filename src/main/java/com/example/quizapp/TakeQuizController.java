package com.example.quizapp;

import com.example.quizapp.interfaces.IAnswerable;
import com.example.quizapp.model.FlashCardController;
import com.example.quizapp.model.Flashcard;
import com.example.quizapp.multiChoice.MultiChoice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class TakeQuizController{

    @FXML private Label quizName;
    @FXML private BorderPane quizHolder;
    @FXML private ProgressBar quizProgress;
    @FXML private Button quizNext;
    @FXML private Button answerButton;
    @FXML private Button hintButton;
    @FXML private Button quizPrevious;


    private IAnswerable specificController;
    private final HashMap<String, AnchorPane> controllers = new HashMap<>();
    private int points = 0;

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
        quizName.setText(quiz.getName());
        quizPrevious.setDisable(true);
        showQuestion();
    }

    public void showNext(){
        quiz.nextQuestion();
        if (!controllers.containsKey(quiz.getCurrentQuestion().getQuestion())) {
            showQuestion();
        }else{
            retrieveQuestion();
        }
        quizPrevious.setDisable(false);
    }

    public void showPrevious(){
        quiz.prevQuestion();
        retrieveQuestion();
        if(quiz.getCurrentQuestion().getQuestion().equals(quiz.getQuestions().get(0).getQuestion())){
            quizPrevious.setDisable(true);
        }
    }

    private void retrieveQuestion() {
        quizHolder.setCenter(controllers.get(quiz.getCurrentQuestion().getQuestion()));
    }

    public void showHint(){
        specificController.showHint();
    }

    public void showAnswer(){
        if (specificController.revealAnswer()){
            points++;
        }
        increaseProgress();
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
            controllers.put(quiz.getCurrentQuestion().getQuestion(), pane);
            quizHolder.setCenter(pane);
        } catch (IOException e){
            e.printStackTrace();
        }

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
