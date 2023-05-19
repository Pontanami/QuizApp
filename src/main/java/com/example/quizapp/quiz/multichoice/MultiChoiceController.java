package com.example.quizapp.quiz.multichoice;

import com.example.quizapp.interfaces.IObservable;
import com.example.quizapp.interfaces.IObserver;
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
public class MultiChoiceController implements IAnswerable, IObservable {
    @FXML private Label multiQuestion;
    @FXML private RadioButton choice1, choice2, choice3, choice4;
    @FXML private Pane quizPane;

    private final RadioButton[] radioButtons = new RadioButton[4];
    private MultiChoice ques;
    private List<String> choiceAnswers;
    private String givenAnswer;
    private List<IObserver> observers = new ArrayList<>();

    /**
     * Initialize the values for the current object with the specified MultiChoice instance
     * @param ques the MultiChoice object associated with this MultiChoiceController instance
     */
    public void initializeData(MultiChoice ques){
        this.ques = ques;
        choiceAnswers = ques.getChoices();
        Collections.shuffle(choiceAnswers);
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
        Alert a;
        if (ques.getHint() != null){
            choiceAnswers = ques.showHint();
            init();
        }
        else {
            a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Hint type: " + "MISSING");

            a.setTitle("Hint");
            a.setContentText("No hint is available");

            Parent parentPane = quizPane.getParent();
            parentPane.setOpacity(0.2);
            a.showAndWait();
            parentPane.setOpacity(1);
        }
    }

    /**
     * Reveals the answer for the current instance of {@link MultiChoice}
     * @return true if answered correctly, false otherwise
     */
    @Override
    public boolean revealAnswer(){
        boolean answer = false;
        boolean oneIsSelected = false;

        for (RadioButton rbs : radioButtons){
            if (rbs.isSelected()){
                givenAnswer = rbs.getText();
                oneIsSelected = true;
            }
        }

        for (RadioButton rb : radioButtons){
            if (rb.getText().equals(ques.getAnswer())){
                if(rb.isSelected()){
                    givenAnswer = rb.getText();
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

    @Deprecated
    public String usersAnswer() {
        return givenAnswer;
    }


    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        observers.remove((IObserver) observer);
    }

    @Override
    public void notifySubscribers() {
        for (IObserver observer : observers){
            observer.update();
        }
    }
}
