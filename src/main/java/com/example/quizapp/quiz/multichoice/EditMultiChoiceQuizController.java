package com.example.quizapp.quiz.multichoice;

import com.example.quizapp.NavigationStack;
import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.quiz.IQuizManager;
import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.quiz.Quiz;
import javafx.fxml.FXML;

public class EditMultiChoiceQuizController extends MultiChoiceQuizController {

    private NavigationStack navigation = NavigationStack.getInstance();
    Quiz quiz;

    public EditMultiChoiceQuizController(Quiz quiz){
        super(quiz);
        this.quiz = quiz;
        addExistingQuestions();

    }

    private void addExistingQuestions(){
        for(IQuizable question : quiz.getQuestions()){
            addQuestion(new CreateMultichoiceController(this, (MultiChoice) question));
        }
    }

    @Override
    public void createQuiz(){
        uploadQuiz();
    }
}
