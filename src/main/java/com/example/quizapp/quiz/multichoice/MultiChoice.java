package com.example.quizapp.quiz.multichoice;

import com.example.quizapp.hints.EliminateChoiceHint;
import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.hints.IHint;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultiChoice implements IQuizable<Object> {
    private final List<String> choices;
    private final String question;
    private final String answer;
    private IHint<List<String>> hint;

    public MultiChoice(String question, String answer, List<String> choices){
        this.choices = choices;
        this.answer = answer;
        this.question = question;
    }

    public MultiChoice(String question, String answer, List<String> choices, IHint<List<String>> hint){
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
        return hint.showHint();
    }

    public IHint<List<String>> getHint() {
        return hint;
    }

    @Override
    public String getQuestion() {
        return question;
    }


}
