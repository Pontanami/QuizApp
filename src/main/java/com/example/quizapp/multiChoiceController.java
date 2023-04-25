package com.example.quizapp;

import com.example.quizapp.model.IHint;
import com.example.quizapp.multiChoice.EliminateChoiceHint;
import com.example.quizapp.multiChoice.MultiChoice;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class multiChoiceController {

    private final Quiz q = new Quiz("Elements");

    @FXML
    private Label quizName;

    @FXML
    private Label multiQuestion;

    @FXML
    private RadioButton choice1, choice2, choice3, choice4;

    @FXML
    private ProgressBar quizProgress;


    private final RadioButton[] radioButtons;

    BigDecimal progress = new BigDecimal("0.0");

    public multiChoiceController(){

        IHint<List<String>> hint1 = new EliminateChoiceHint(new ArrayList<>(Arrays.asList("1990", "1996",
                "1995", "1992")), "3");

        MultiChoice multiChoice1 = new MultiChoice("Which year was Java created ?", "1995",
                new ArrayList<>(Arrays.asList("1990", "1996", "1995", "1992")), hint1);


        IHint<List<String>> hint2 = new EliminateChoiceHint(new ArrayList<>(Arrays.asList("Supernova", "Nebula",
                "Planet", "Star")), "4");

        MultiChoice multiChoice2 = new MultiChoice("What is sun ?", "Star",
                new ArrayList<>(Arrays.asList("Supernova", "Nebula", "Planet", "Star")), hint2);


        q.addQuestion(multiChoice1);
        q.addQuestion(multiChoice2);

        radioButtons = new RadioButton[4];

    }

    private void changeQuizName(){
        String s = q.getName();
        quizName.setText(s);
    }

    private void changeQuestionName(){
        String s = q.getCurrentQuestion().getQuestion();
        multiQuestion.setText(s);
    }

    private void changeRadioButtonNames(){
        List<String> answers;
        MultiChoice currentQues = (MultiChoice) q.getCurrentQuestion();

        answers = currentQues.getChoices();

        for (int i = 0; i < answers.size(); i++){
            radioButtons[i].setText(answers.get(i));
            radioButtons[i].setStyle("-fx-text-fill: white");
            radioButtons[i].setSelected(false);
        }
    }

    public void checkAnswer(){
        for (RadioButton rb : radioButtons){
            if (rb.getText().equals(q.getCurrentQuestion().getAnswer())){
                rb.setStyle("-fx-text-fill: rgba(60,157,35,1) 100%");
            }
            else {
                rb.setStyle("-fx-text-fill: rgba(237,32,84,1) 100%");
            }
        }

        increaseProgress();
        q.nextQuestion();

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(event -> {
            changeQuizName();
            changeQuestionName();
            changeRadioButtonNames();
        });
        delay.play();


    }

    private void increaseProgress(){
        double n = q.getQuestions().size();
        double step = 1.0 / n;

        progress = progress.setScale(2, RoundingMode.HALF_EVEN);

        if (progress.doubleValue() < 1){
            progress = new BigDecimal(Double.toString(progress.doubleValue() + step));
            quizProgress.setProgress(progress.doubleValue());
        }
    }

    @FXML
    public void initialize() {
        changeQuizName();
        changeQuestionName();

        radioButtons[0] = choice1;
        radioButtons[1] = choice2;
        radioButtons[2] = choice3;
        radioButtons[3] = choice4;

        changeRadioButtonNames();

        quizProgress.setStyle("-fx-accent: #542db5");
    }

}
