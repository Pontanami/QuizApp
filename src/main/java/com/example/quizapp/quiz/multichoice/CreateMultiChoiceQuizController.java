package com.example.quizapp.quiz.multichoice;


import com.example.quizapp.NavigationStack;
import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.quiz.Quiz;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class CreateMultiChoiceQuizController extends MultiChoiceQuizController {

    private Quiz quiz = new Quiz();

    private NavigationStack navigation = NavigationStack.getInstance();

    /**
     * Creates a CreateMultiChoiceQuiz
     */
    public CreateMultiChoiceQuizController() {
        super(new Quiz());
        subscribe((IObserver) navigation.getSpecificView(HomeController.class));
    }

    /**
     * This method adds a new CreateFlashcard controller to CreateFlashCardQuiz
     */

    @FXML
    public void addQuestion(){
        CreateMultichoiceController multichoice = new CreateMultichoiceController(this);
        addQuestion(multichoice);
    }

    /**
     * This method adds all questions to the quiz and pushes the quiz to repository
     */
    @Override
    protected void submitQuiz(){
        initQuizCreation();
        quizRepository.uploadQuiz(quiz, userRepository.getCurrentUser());
        notifySubscribers();
        navigateToQuizCollection();
    }
}
