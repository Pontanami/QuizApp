package com.example.quizapp.user;

import com.example.quizapp.Quiz;
import com.example.quizapp.QuizQuery;

import java.util.List;

public interface IQuizRepository {
    List<Quiz> getQuiz(QuizQuery.QuizQueryBuilder query);
    void uploadQuiz(Quiz quiz, User currentUser);
}
