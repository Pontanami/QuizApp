package com.example.quizapp.quiz.multichoice;


import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.firebase.IQuizRepository;
import com.example.quizapp.firebase.IUserRepository;
import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.quiz.Quiz;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class CreateMultiChoiceQuizController extends MultiChoiceQuizController {

    private Quiz quiz;

    private NavigationStack navigation = NavigationStack.getInstance();
    private IUserRepository userRepository = FirebaseUserRepository.getAuth();
    private IQuizRepository quizRepository = new FirebaseQuizRepository();

    /**
     * Creates a CreateMultiChoiceQuiz
     */
    public CreateMultiChoiceQuizController() {
        super(new Quiz());
        quiz = getQuiz();
        subscribe((IObserver) navigation.getSpecificView(HomeController.class));
    }


    @FXML
    public void addQuestion(){
        addQuestion(new CreateMultichoiceController(this));
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
