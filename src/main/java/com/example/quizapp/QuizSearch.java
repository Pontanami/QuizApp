package com.example.quizapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QuizSearch {
    public static List<Quiz> search(List<Quiz> quizList, String searchQuery){
        List<Quiz> quizzes = new ArrayList<>();
        for (Quiz quiz : quizList){
            if(quiz.getName().toLowerCase().startsWith(searchQuery))
                quizzes.add(quiz);
        }
        return quizzes;
    }
}
