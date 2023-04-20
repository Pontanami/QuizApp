package com.example.quizapp.multiChoice;

import com.example.quizapp.interfaces.IQuizable;

import java.util.Arrays;
import java.util.InputMismatchException;

public class MultiChoice implements IQuizable<String[]> {
    private final String[] choices;
    private String correctAnswer;
    private final String question;
    private static int points = 0; // Should be replaced with the user's database point variable

    public MultiChoice(String question, String[] answers){
        choices = answers;
        //setCorrectAnswer(choices[4]);
        this.question = question;
    }
    @Override
    public String[] getAnswer() {
        return choices;
    }

    /**
     * @param correct An argument that specifies which of the given answers is the correct one
     * @exception IllegalArgumentException In case of invalid correct answer
     */
    private void setCorrectAnswer(String correct){
        if (Arrays.asList("1", "2", "3", "4").contains(correct)){
            correctAnswer = correct;
        }
        else{
            throw new InputMismatchException("The alternative " + correct + " is not one of the four choices");
        }
    }

    public String getCorrectAnswer(){
        return correctAnswer;
    }

    public void addPoint(){
        points++;
    }

    @Override
    public String getQuestion() {
        return question;
    }

    public int getPoints() {
        return points;
    }
}
