package com.example.quizapp.model;

public class HalfWordHint implements IHint<String> {


    private String answer;

    /**
     * Creates a strategy for half of the word hint
     * @param answer is the correct answer to the question
     */
    public HalfWordHint(String answer) {
        this.answer = answer;
    }

    /**
     * Reveals half of the correct answer
     * @return half of the correct answer
     */
    @Override
    public String showHint() {
        String hintAnswer = answer.substring(0, Math.round(answer.length()/2));
        return hintAnswer;
    }
}