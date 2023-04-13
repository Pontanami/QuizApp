package com.example.quizapp.multiChoice;

import java.util.ArrayList;
import java.util.InputMismatchException;

public class MultiChoiceModel {
    private final ArrayList<String> choices = new ArrayList<>();
    private String correctAnswer;
    private final String question;
    private int points = 0; // Should be replaced with the user's database point variable

    protected MultiChoiceModel(String question, String answer1, String answer2, String answer3, String answer4){
        choices.add(answer1);
        choices.add(answer2);
        choices.add(answer3);
        choices.add(answer4);
        this.question = question;
    }

    protected ArrayList<String> getChoices() {
        return choices;
    }

    /**
     * @param correct An argument that specifies which of the given answers is the correct one
     * @exception IllegalArgumentException In case of invalid correct answer
     */
    protected void setCorrectAnswer(String correct){
        if (choices.contains(correct) && !correct.isEmpty()){
            correctAnswer = correct;
        }
        else{
            throw new InputMismatchException("the answer " + correct + " is not one of the four choices");
        }
    }

    protected String getCorrectAnswer(){
        return correctAnswer;
    }

    protected void addPoint(){
        points++;
    }
}
