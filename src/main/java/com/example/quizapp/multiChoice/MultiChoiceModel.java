package com.example.quizapp.multiChoice;

import com.example.quizapp.model.IHint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;

public class MultiChoiceModel {
    private final ArrayList<String> choices = new ArrayList<>();
    private String correctAnswer;
    private final String question;
    private IHint<List<String>> hint;
    private static int points = 0; // Should be replaced with the user's database point variable

    protected MultiChoiceModel(String question, String answer1, String answer2, String answer3, String answer4){
        choices.add(answer1);
        choices.add(answer2);
        choices.add(answer3);
        choices.add(answer4);
        this.question = question;
    }

    protected MultiChoiceModel(String question, String answer1, String answer2, String answer3, String answer4, IHint hint){
        choices.add(answer1);
        choices.add(answer2);
        choices.add(answer3);
        choices.add(answer4);
        this.question = question;
        this.hint = hint;
    }

    protected ArrayList<String> getChoices() {
        return choices;
    }

    /**
     * @param correct An argument that specifies which of the given answers is the correct one
     * @exception IllegalArgumentException In case of invalid correct answer
     */
    protected void setCorrectAnswer(String correct){
        /*if (Arrays.asList("1", "2", "3", "4").contains(correct)){
            correctAnswer = correct;
        }*/
        if (choices.contains(correct)){
            correctAnswer = correct;
        }
        else{
            throw new InputMismatchException("The alternative " + correct + " is not one of the four choices");
        }
    }

    protected String getCorrectAnswer(){
        return correctAnswer;
    }

    protected void addPoint(){
        points++;
    }

    public String getQuestion() {
        return question;
    }

    public int getPoints() {
        return points;
    }

    public IHint<List<String>> getHint() {
        return hint;
    }

    public List<String> showHint(){
        return hint.showHint();
    }
}
