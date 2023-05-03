package com.example.quizapp.quiz;

public interface ICreateQuestion<T> {
    IQuizable<T> createQuestion();
    void removeQuestion();
}
