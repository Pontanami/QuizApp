package com.example.quizapp;

import com.example.quizapp.interfaces.IAnswerable;
import com.example.quizapp.model.FlashCardController;
import com.example.quizapp.model.Flashcard;
import com.example.quizapp.multiChoice.MultiChoice;
import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TakeQuizController{

    @FXML
    private Label QuizName;


    private IAnswerable specificController;
    private final HashMap<String, AnchorPane> controllers = new HashMap<>();
    private int points = 0;

    @FXML private Button QuizAnswer;
    @FXML private Button QuizHint;
    @FXML private Button finishButton;

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

    private final List<String> answeredQuestions = new ArrayList<>();

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
        QuizPrevious.setDisable(true);
        QuizPoints.setText("Points: " + points + "/" + quiz.getQuestions().size());
        showQuestion();

    }

    public void showNext(){
        quiz.nextQuestion();
        showQuestion();
        if (quiz.getCurrentQuestion().getQuestion().equals(quiz.getQuestions().get(quiz.getQuestions().size()-1).getQuestion())){
            QuizNext.setVisible(false);
            finishButton.setVisible(true);
        }
        QuizPrevious.setDisable(false);
        isAnswered();
    }

    public void showPrevious(){
        quiz.prevQuestion();
        QuizNext.setVisible(true);
        finishButton.setVisible(false);
        showQuestion();
        if(quiz.getCurrentQuestion().getQuestion().equals(quiz.getQuestions().get(0).getQuestion())){
            QuizPrevious.setDisable(true);
        }
        isAnswered();
    }

    private void retrieveQuestion() {
        QuizHolder.setCenter(controllers.get(quiz.getCurrentQuestion().getQuestion()));
    }

    public void showHint(){
        if (QuizAnswer.isDisabled()){
            QuizHint.setDisable(true);
        }
        else {
            specificController.showHint();
            QuizHint.setDisable(true);
        }
    }

    public void showAnswer(){
        if (specificController.revealAnswer()){
            points++;
            QuizPoints.setText("Points: " + points + "/" + quiz.getQuestions().size());
        }
        QuizAnswer.setDisable(true);
        QuizHint.setDisable(true);
        answeredQuestions.add(quiz.getCurrentQuestion().getQuestion());
    }

    private void isAnswered(){
        QuizAnswer.setDisable(answeredQuestions.contains(quiz.getCurrentQuestion().getQuestion()));
    }

    private void showQuestion() {

        if (answeredQuestions.contains(quiz.getCurrentQuestion().getQuestion())){
            retrieveQuestion();
            QuizHint.setDisable(true);
        }
        else {
            QuizHint.setDisable(false);
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
                increaseProgress();
                QuizHolder.setCenter(pane);
            } catch (IOException e){
                e.printStackTrace();
            }
        }


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

    public void finish(){
        Platform.exit();
    }

}
