package com.example.quizapp.interfaces;

import com.example.quizapp.controllers.CreateFlashcard;
import com.example.quizapp.model.Flashcard;
import javafx.scene.layout.Pane;

public interface IFlashcardManager {
    void removeQuestion(CreateFlashcard question);
}
