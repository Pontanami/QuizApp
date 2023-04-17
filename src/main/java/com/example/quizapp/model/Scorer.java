package com.example.quizapp.model;

public class Scorer {

    public static int scoreQuestion(int userPoints, boolean isCorrect){
        if (isCorrect){
            return ++userPoints;
        }
        return userPoints;

    }


}