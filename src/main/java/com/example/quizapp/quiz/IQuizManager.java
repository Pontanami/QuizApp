package com.example.quizapp.quiz;

public interface IQuizManager<T> {
    void removeQuestion(ICreateQuestion<T> question);
    void addQuestion();
}
