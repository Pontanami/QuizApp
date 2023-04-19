package com.example.quizapp.model;

public class TextHint implements IWordHint{
    private String hint;

    public TextHint(String hint){
        this.hint = hint;
    }
    @Override
    public String showHint() {
        return hint;
    }
}
