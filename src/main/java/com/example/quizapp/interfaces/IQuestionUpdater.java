package com.example.quizapp.interfaces;

public interface IQuestionUpdater<T> {
    void update(IQuizable<T> question);
}
