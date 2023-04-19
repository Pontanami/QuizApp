package com.example.quizapp.model;

public class TextHint implements IHint {
    private String hint;

    public TextHint(String hint){
        this.hint = hint;
    }
    @Override
    public String showHint() {
        return hint;
    }
}
