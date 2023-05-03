package com.example.quizapp.quiz.multichoice;

import com.example.quizapp.hints.EliminateChoiceHint;
import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.hints.IHint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiChoice implements IQuizable<Object> {
    private final List<String> choices;
    private final String question;
    private final String answer;
    private IHint<Object> hint;

    public MultiChoice(String question, String answer, List<String> choices){
        this.choices = choices;
        this.answer = answer;
        this.question = question;
    }

    public MultiChoice(String question, String answer, List<String> choices, IHint hint){
        this.choices = choices;
        this.answer = answer;
        this.question = question;
        this.hint = hint;

    }

    public List<String> getChoices() {
        return choices;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public List<String> showHint() {
        List<String> clues = new ArrayList<>();
        String clue;

        if (hint == null){
            clues.add("No hint available");
        }
        else if (hint.showHint().getClass().equals(String.class)){
            clue = (String) hint.showHint();
            clues.add(clue);
        }
        else {
            clues = (List<String>) hint.showHint();
        }

        return clues;
    }

    public IHint<Object> getHint() {
        return hint;
    }

    @Override
    public String getQuestion() {
        return question;
    }


}
