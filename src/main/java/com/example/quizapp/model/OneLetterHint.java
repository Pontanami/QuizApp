package com.example.quizapp.model;

public class OneLetterHint implements IWordHint{

    private String answer;
    private int hintLength;

    public OneLetterHint(String answer){
        this.answer = answer;
        this.hintLength = 1;
    }

    @Override
    public String showHint() {
        String hintAnswer = answer.substring(0, hintLength);

        if(hintLength < answer.length())
            hintLength++;

        return hintAnswer;
    }
}
