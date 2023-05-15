package com.example.quizapp.firebase;

import com.example.quizapp.quiz.Quiz;
import com.example.quizapp.quiz.QuizQuery;
import com.example.quizapp.user.User;

import java.util.List;

public interface IQuizRepository {
    List<Quiz> getQuiz(QuizQuery.QuizQueryBuilder query);
    Quiz getSingleQuiz(QuizQuery.QuizQueryBuilder query);
    void uploadQuiz(Quiz quiz, User currentUser);
    void removeQuiz(String id);
}
