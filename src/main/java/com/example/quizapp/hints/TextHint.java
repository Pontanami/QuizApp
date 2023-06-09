package com.example.quizapp.hints;

public class TextHint implements IHint<String> {
    private final String hint;

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
