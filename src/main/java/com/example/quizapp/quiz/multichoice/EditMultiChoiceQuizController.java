package com.example.quizapp.quiz.multichoice;

import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.quiz.Quiz;
import javafx.fxml.FXML;

public class EditMultiChoiceQuizController extends MultiChoiceQuizController {

    Quiz quiz;

    public EditMultiChoiceQuizController(Quiz quiz){
        super(quiz);
        this.quiz = quiz;
        addExistingQuestions();
    }

    @FXML
    public void addQuestion(){
        CreateMultichoiceController multichoice = new CreateMultichoiceController(this);
        addQuestion(multichoice);
    }

    /**
     * Adds all existing questions to the quiz
     */
    private void addExistingQuestions(){
        for(IQuizable question : quiz.getQuestions()){
            addQuestion(new CreateMultichoiceController(this, (MultiChoice) question));
        }
    }

    /**
     * Adds all questions to the quiz and pushes the quiz to database
     */
    @Override
    protected void submitQuiz(){
        initQuizCreation();
        FirebaseQuizRepository quizRepository = new FirebaseQuizRepository();
        quizRepository.updateQuiz(quiz);
        notifySubscribers();
        navigateToQuizCollection();
    }
}
