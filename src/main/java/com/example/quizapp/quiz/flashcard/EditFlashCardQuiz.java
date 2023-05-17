package com.example.quizapp.quiz.flashcard;

import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.quiz.Quiz;
import javafx.fxml.FXML;

public class EditFlashCardQuiz extends FlashCardQuizController{

    Quiz quiz;

    public EditFlashCardQuiz(Quiz quiz){
        super(quiz);
        this.quiz = quiz;
        addExistingQuestions();
    }

    /**
     * This method adds a new CreateFlashcard controller to CreateFlashCardQuiz
     */
    @FXML
    public void addQuestion(){
        CreateFlashcardController flashcard = new CreateFlashcardController(this);
        addQuestion(flashcard);
    }


    private void addExistingQuestions(){
        for(IQuizable question : quiz.getQuestions()){
            addQuestion(new CreateFlashcardController(this, (Flashcard) question));
        }
    }

    @Override
    protected void submitQuiz() {
        initQuizCreation();
        FirebaseQuizRepository quizRepository = new FirebaseQuizRepository();
        quizRepository.updateQuiz(quiz);
        notifySubscribers();
        navigateToQuizCollection();
    }
}
