package com.example.quizapp.quiz;

import com.example.quizapp.quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizSearch {
    public static List<Quiz> search(List<Quiz> quizList, String searchQuery){
        List<Quiz> quizzes = new ArrayList<>();
        for (Quiz quiz : quizList){
            if(quiz.getName().toLowerCase().startsWith(searchQuery.toLowerCase()))
                quizzes.add(quiz);
        }
        return quizzes;
    }
}
