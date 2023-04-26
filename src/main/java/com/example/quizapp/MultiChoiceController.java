package com.example.quizapp;

import com.example.quizapp.model.IHint;
import com.example.quizapp.multiChoice.EliminateChoiceHint;
import com.example.quizapp.multiChoice.MultiChoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import java.util.List;
import java.util.concurrent.Flow;

public class MultiChoiceController {

    @FXML
    private Label multiQuestion;
    @FXML
    private Button hintButton;
    @FXML
    private Button showAnswerButton;
    @FXML
    private RadioButton choice1, choice2, choice3, choice4;

    private final RadioButton[] radioButtons = new RadioButton[4];

    private int points = 0;
    private boolean hasAnswered = false;
    private MultiChoice ques;
    List<String> choiceAnswers;


    public void initializeData(MultiChoice ques){
        this.ques = ques;
        choiceAnswers = ques.getChoices();

        init();
    }

    private void init() {
        changeQuestionName();

        radioButtons[0] = choice1;
        radioButtons[1] = choice2;
        radioButtons[2] = choice3;
        radioButtons[3] = choice4;

        changeRadioButtonNames();
    }

    private void changeQuestionName(){
        String s = ques.getQuestion();
        multiQuestion.setText(s);
    }

    private void changeRadioButtonNames(){
        for (RadioButton rb : radioButtons){
            rb.setVisible(false);
        }
        for (int i = 0; i < choiceAnswers.size(); i++){
            radioButtons[i].setText(choiceAnswers.get(i));
            radioButtons[i].setSelected(false);
            radioButtons[i].setVisible(true);
        }
    }

    public void checkAnswer(){
        for (RadioButton rb : radioButtons){
            if (rb.getText().equals(ques.getAnswer())){
                if(rb.isSelected()){
                    System.out.println("thats correct"); //Somehow communicate with TakeQuizController to call quiz.addPoint()
                    points++;
                }
                rb.setId("answer");
            }
            else {
                rb.setDisable(true);
                rb.setId("wrongAnswer");
            }
        }

        showAnswerButton.setDisable(true);
        hintButton.setDisable(true);
    }

    public void useHint(){
        choiceAnswers = ques.showHint();
        hintButton.setDisable(true);
        init();
    }

    public int getPoints() {
        return points;
    }
}
