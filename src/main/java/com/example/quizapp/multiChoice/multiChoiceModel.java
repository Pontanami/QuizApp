package com.example.quizapp.multiChoice;

import java.util.ArrayList;

public class multiChoiceModel{
    private final ArrayList<String> choices = new ArrayList<String>();
    private String correctAnswer;
    private int points = 0;

    protected multiChoiceModel(String answer1, String answer2, String answer3, String answer4){
        choices.add(answer1);
        choices.add(answer2);
        choices.add(answer3);
        choices.add(answer4);
    }

    protected ArrayList<String> getChoices() {
        return choices;
    }

    protected void setCorrectAnswer(String correct){
        if (choices.contains(correct) && !correct.isEmpty()){
            correctAnswer = correct;
        }
        else{
            throw new IllegalArgumentException("the answer " + correct + " is not one of the four choices");
        }
    }

    protected String getCorrectAnswer(){
        return correctAnswer;
    }

    protected void addPoint(){
        points++;
    }
}
