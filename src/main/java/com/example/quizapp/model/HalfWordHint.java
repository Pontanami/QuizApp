package com.example.quizapp.model;

public class HalfWordHint implements IWordHint{


    private String answer;

    public HalfWordHint(String answer) {
        this.answer = answer;
    }

    @Override
    public String showHint() {
        String hintAnswer = answer.substring(0, Math.round(answer.length()/2));
        return hintAnswer;
    }
}
