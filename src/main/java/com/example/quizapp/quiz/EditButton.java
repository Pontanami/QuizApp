package com.example.quizapp.quiz;

import com.example.quizapp.NavigationStack;
import com.example.quizapp.quiz.flashcard.EditFlashCardQuiz;
import com.example.quizapp.quiz.flashcard.Flashcard;
import com.example.quizapp.quiz.multichoice.EditMultiChoiceQuizController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class EditButton extends AnchorPane {
    private Quiz quiz;
    private NavigationStack navigation = NavigationStack.getInstance();
    public EditButton(Quiz quiz) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editButton.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.quiz = quiz;
    }

    @FXML
    private void editQuiz(){
        if (quiz.getQuestions().get(0) instanceof Flashcard) {
            editFlashcardQuiz();
        } else {
            editMultiChoiceQuiz();
        }
    }

    /**
     * Navigates to the view of editing a {@link com.example.quizapp.quiz.flashcard.Flashcard} question
     */
    private void editFlashcardQuiz() {
        EditFlashCardQuiz editFlashCardQuiz = new EditFlashCardQuiz(quiz);
        navigation.pushView(editFlashCardQuiz);
    }

    /**
     * Navigates to the view of editing a {@link com.example.quizapp.quiz.multichoice.MultiChoice} question
     */
    private void editMultiChoiceQuiz() {
        EditMultiChoiceQuizController editMultiChoiceQuizController = new EditMultiChoiceQuizController(quiz);
        navigation.pushView(editMultiChoiceQuizController);
    }
}
