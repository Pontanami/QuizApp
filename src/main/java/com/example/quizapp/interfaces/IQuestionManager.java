package com.example.quizapp.interfaces;

import com.example.quizapp.controllers.CreateFlashcard;

public interface IQuestionManager {
    void removeQuestion(CreateFlashcard question);
    void addQuestion();
}
