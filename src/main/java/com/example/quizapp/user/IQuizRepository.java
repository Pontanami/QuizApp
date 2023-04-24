package com.example.quizapp.user;

import com.example.quizapp.Quiz;

public interface IQuizRepository {
    Quiz getQuiz();
    void uploadQuiz(Quiz quiz);
}
