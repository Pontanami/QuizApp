package com.example.quizapp;

import com.example.quizapp.interfaces.IAnswerable;
import com.example.quizapp.model.IHint;
import com.example.quizapp.multiChoice.EliminateChoiceHint;
import com.example.quizapp.multiChoice.MultiChoice;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import java.util.List;

public class MultiChoiceController implements IAnswerable {

    @FXML
    private Label multiQuestion;

    @FXML
    private RadioButton choice1, choice2, choice3, choice4;

    private final RadioButton[] radioButtons = new RadioButton[4];

    private MultiChoice ques;
    List<String> choices;

    public void initializeData(MultiChoice ques){
        this.ques = ques;
        choices = ques.getChoices();

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
        for (int i = 0; i < choices.size(); i++){
            radioButtons[i].setText(choices.get(i));
            radioButtons[i].setSelected(false);
            radioButtons[i].setVisible(true);
        }
    }

    @Override
    public void showHint() {
        choices = ques.showHint();
        init();
    }

    @Override
    public boolean revealAnswer(){
        boolean answer = false;
        for (RadioButton rb : radioButtons){
            if (rb.getText().equals(ques.getAnswer())){
                if(rb.isSelected()){
                    System.out.println("thats correct"); //Somehow communicate with TakeQuizController to call quiz.addPoint()
                    answer = true;
                }
                rb.setId("answer");
            }
            else {
                rb.setDisable(true);
                rb.setId("wrongAnswer");
            }
        }
        return answer;
    }

}
