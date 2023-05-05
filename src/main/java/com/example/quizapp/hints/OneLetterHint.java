package com.example.quizapp.hints;

public class OneLetterHint implements IHint<String> {

    private final String answer;
    private int hintLength;

    /**
     * Creates a OneLetterHint object
     * @param answer the correct answer to the question
     */
    public OneLetterHint(String answer){
        this.answer = answer;
        this.hintLength = 1;
    }

    /**
     * Reveals one additional letter of the answer for each call
     * @return the hint with incremented number of characters
     */
    @Override
    public String showHint() {
        String hintAnswer = answer.substring(0, hintLength);

        if(hintLength < answer.length())
            hintLength++;

        return hintAnswer;
    }
}
