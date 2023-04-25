package com.example.quizapp.user;

import com.example.quizapp.Quiz;

public interface IQuizRepository {
    Quiz getQuiz(String id);
    void uploadQuiz(Quiz quiz, User currentUser);
}
