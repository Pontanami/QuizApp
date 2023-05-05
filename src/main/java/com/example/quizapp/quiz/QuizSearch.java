package com.example.quizapp.quiz;

import com.example.quizapp.quiz.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizSearch {
    /**
     * A static method that can search through a list of {@link Quiz} quizzes by their name attribute.
     * @param quizList The list of {@link Quiz} quizzes to search through
     * @param searchQuery The query to use when searching
     * @return A new list of the quizzes that start with the searchQuery
     */
    public static List<Quiz> search(List<Quiz> quizList, String searchQuery){
        List<Quiz> quizzes = new ArrayList<>();
        for (Quiz quiz : quizList){
            if(quiz.getName().toLowerCase().startsWith(searchQuery.toLowerCase()))
                quizzes.add(quiz);
        }
        return quizzes;
    }
}
