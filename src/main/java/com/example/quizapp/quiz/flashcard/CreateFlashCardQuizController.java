package com.example.quizapp.quiz.flashcard;

import com.example.quizapp.quiz.InputValidator;
import com.example.quizapp.NavigationStack;
import com.example.quizapp.interfaces.IObservable;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.quiz.*;
import com.example.quizapp.quiz.multichoice.CreateMultichoiceController;
import com.example.quizapp.quiz.tags.Tag;
import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.quiz.tags.Subject;
import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.firebase.IQuizRepository;
import com.example.quizapp.firebase.IUserRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateFlashCardQuizController extends FlashCardQuizController {

    private Quiz quiz;

    private NavigationStack navigation = NavigationStack.getInstance();

    /**
     * Creates a CreateFlashCardQuiz
     */
    public CreateFlashCardQuizController() {
        super(new Quiz());
        this.quiz = getQuiz();
    }

    /**
     * This method adds a new CreateFlashcard controller to CreateFlashCardQuiz
     */
    @FXML
    public void addQuestion(){
        CreateFlashcardController flashcard = new CreateFlashcardController(this);
        addQuestion(flashcard);
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
