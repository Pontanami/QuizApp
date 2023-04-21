package com.example.quizapp.multiChoice;

import com.example.quizapp.interfaces.IQuizable;

import java.util.Arrays;
import java.util.InputMismatchException;

public class MultiChoice implements IQuizable<String[]> {
    private final String[] choices;
    private final String question;

    public MultiChoice(String question, String[] answers){
        choices = answers;

        if (!Arrays.asList("1", "2", "3", "4").contains(choices[4])){
            throw new InputMismatchException("The alternative " + choices[4] + " is not one of the four choices");
        }

        this.question = question;
    }
    @Override
    public String[] getAnswer() {
        return choices;
    }

    @Override
    public String getQuestion() {
        return question;
    }
}
