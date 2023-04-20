package com.example.quizapp.model;

public class TextHint implements IHint {
    private String hint;

    /**
     * Creates a hint where the user gives text as hint
     * @param hint the specified hint in text
     */
    public TextHint(String hint){
        this.hint = hint;
    }

    /**
     * Reveals the text hint to the question
     * @return text hint to question
     */
    @Override
    public String showHint() {
        return hint;
    }
}
