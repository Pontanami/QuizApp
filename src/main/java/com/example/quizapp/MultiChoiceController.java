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
import java.util.concurrent.Flow;

/**
 * Represents the controller of multiChoice.fxml
 * Method initializeData(MultiChoice ques) must be called with an instance of MultiChoice
 * @see MultiChoiceController#initializeData(MultiChoice)
 */
public class MultiChoiceController implements IAnswerable {
    @FXML private Label multiQuestion;
    @FXML private RadioButton choice1, choice2, choice3, choice4;

    private final RadioButton[] radioButtons = new RadioButton[4];
    private MultiChoice ques;
    private List<String> choiceAnswers;
    private List<String> hintAnswers;

    /**
     * Initialize the values for the current object with the specified MultiChoice instance
     * @param ques the MultiChoice object associated with this MultiChoiceController instance
     */
    public void initializeData(MultiChoice ques){
        this.ques = ques;
        choiceAnswers = ques.getChoices();
        hintAnswers = ques.showHint();
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

    /**
     * Shows the hint for the current instance of MultiChoice
     */
    @Override
    public void showHint() {
        choiceAnswers = hintAnswers;
        init();
    }

    /**
     * Reveals the answer for the current instance of MultiChoice
     * @return true if answered correctly false otherwise
     */
    @Override
    public boolean revealAnswer(){
        boolean answer = false;
        boolean oneIsSelected = false;
        for (RadioButton rb : radioButtons){
            if (rb.isSelected()){
                oneIsSelected = true;
            }

            if (rb.getText().equals(ques.getAnswer())){
                if(rb.isSelected()){
                    answer = true;
                }
                else if (!oneIsSelected){
                    rb.setSelected(true);
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
