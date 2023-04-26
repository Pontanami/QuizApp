package com.example.quizapp.interfaces;

import com.example.quizapp.model.Flashcard;

public interface ICreateQuestion<T> {
    IQuizable<T> createQuestion();
    void removeQuestion();
}
