package com.example.quizapp;

import com.example.quizapp.model.IHint;
import com.example.quizapp.multiChoice.EliminateChoiceHint;
import com.example.quizapp.multiChoice.MultiChoice;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiChoiceController {

    @FXML
    private Label multiQuestion;

    @FXML
    private RadioButton choice1, choice2, choice3, choice4;

    private final RadioButton[] radioButtons = new RadioButton[4];

    private MultiChoice ques;

    public void initializeData(MultiChoice ques){
        this.ques = ques;
    }

    private void changeQuestionName(){
        String s = ques.getQuestion();
        multiQuestion.setText(s);
    }

    private void changeRadioButtonNames(){
        List<String> answers = ques.getChoices();

        for (int i = 0; i < answers.size(); i++){
            radioButtons[i].setText(answers.get(i));
            radioButtons[i].setStyle("-fx-text-fill: white");
            radioButtons[i].setSelected(false);
        }
    }

    public void checkAnswer(){
        for (RadioButton rb : radioButtons){
            if (rb.getText().equals(ques.getAnswer())){
                rb.setStyle("-fx-text-fill: rgba(60,157,35,1) 100%");
            }
            else {
                rb.setStyle("-fx-text-fill: rgba(237,32,84,1) 100%");
            }
        }
    }

    @FXML
    public void initialize() {
        changeQuestionName();

        radioButtons[0] = choice1;
        radioButtons[1] = choice2;
        radioButtons[2] = choice3;
        radioButtons[3] = choice4;

        changeRadioButtonNames();
    }

}
