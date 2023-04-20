package com.example.quizapp.model;

public class Scorer {

    /**
     * Adds points to the user
     * @param userPoints the current user points
     * @param isCorrect whether the correct answer was given or not
     * @return the updated user score
     */
    public static int scoreQuestion(int userPoints, boolean isCorrect){
        if (isCorrect){
            return ++userPoints;
        }
        return userPoints;

    }


}