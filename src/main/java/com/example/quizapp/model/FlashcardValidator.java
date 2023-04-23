package com.example.quizapp.model;

public class FlashcardValidator{
    /**
     * Validates the user input
     * @param isCorrect User input 1 for being correct, or anything else for being false.
     * @return true if they were correct.
     */
    public static boolean validate(String isCorrect) {
        return isCorrect.equals("1");
    }
}
