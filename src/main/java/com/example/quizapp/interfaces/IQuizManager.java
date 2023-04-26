package com.example.quizapp.interfaces;

public interface IQuizManager<T> {
    void removeQuestion(ICreateQuestion<T> question);
    void addQuestion();
    void addTag();
    void removeTag();
}
