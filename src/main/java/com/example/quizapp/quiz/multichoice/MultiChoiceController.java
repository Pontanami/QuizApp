package com.example.quizapp.quiz.multichoice;

import com.example.quizapp.quiz.takeQuiz.IAnswerable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the controller of multiChoice.fxml
 * Method initializeData(MultiChoice ques) must be called with an instance of MultiChoice
 * @see MultiChoiceController#initializeData(MultiChoice)
 */
public class MultiChoiceController implements IAnswerable {
    @FXML private Label multiQuestion;
    @FXML private RadioButton choice1, choice2, choice3, choice4;
    @FXML private Pane quizPane;

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
        Collections.shuffle(choiceAnswers);
        hints();
        init();
    }

    private void hints(){
        if (ques.showHint().size() > 1){
            hintAnswers = ques.showHint();
        }else {
            hintAnswers = new ArrayList<>();
            hintAnswers.add(ques.showHint().get(0));
        }
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
        Alert a;
        if (hintAnswers.size() > 1){
            choiceAnswers = hintAnswers;
            init();
        }
        else {
            if (hintAnswers.get(0).equals("No hint available")) {
                a = new Alert(Alert.AlertType.ERROR);
                a.setHeaderText("Hint type: " + "MISSING");
            }else {
                String[] hintName = ques.getHint().getClass().getName().split("\\.");
                a = new Alert(Alert.AlertType.INFORMATION);
                a.setHeaderText("Hint type: " + hintName[hintName.length - 1]);
            }
            a.setTitle("Hint");
            a.setContentText(hintAnswers.get(0));

            Parent parentPane = quizPane.getParent();
            parentPane.setOpacity(0.2);
            a.showAndWait();
            parentPane.setOpacity(1);
        }
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
