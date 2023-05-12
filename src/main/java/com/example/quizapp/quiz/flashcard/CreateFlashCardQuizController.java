package com.example.quizapp.quiz.flashcard;

import com.example.quizapp.quiz.InputValidator;
import com.example.quizapp.NavigationStack;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.quiz.*;
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

public class CreateFlashCardQuizController extends AnchorPane implements IQuizManager<CreateFlashcardController>, Initializable, IObserver {
    private AnchorPane rootpane;

    @FXML
    private AnchorPane tagPane;

    @FXML
    private AnchorPane flashcardPane;

    @FXML
    private VBox items;

    private List<CreateFlashcardController> questions = new ArrayList<>();

    @FXML
    private ScrollPane flashcardScrollpane;

    @FXML
    private FlowPane tagBox;

    @FXML
    private VBox appliedTagBox;

    @FXML
    private TextField quizName;

    private Quiz quiz = new Quiz();

    private IQuizRepository quizRepository = new FirebaseQuizRepository();
    private IUserRepository userRepository = FirebaseUserRepository.getAuth();

    NavigationStack navigationStack = NavigationStack.getInstance();

    private InputValidator validator = new InputValidator();



    /**
     * Creates a CreateFlashCardQuiz
     */
    public CreateFlashCardQuizController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createFlashcardQuiz.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        quiz.subscribe(this);
        validator.createValidationTextField(quizName);
    }

    /**
     * This method adds a new CreateFlashcard controller to CreateFlashCardQuiz
     */
    @FXML
    @Override
    public void addQuestion(){
        CreateFlashcardController flashcard = new CreateFlashcardController(this);
        questions.add(flashcard);
        updateCreatedQuestions();
    }

    /**
     * This method removes a question controller from CreateFlashCardQuiz
     * @param flashcard to be removed
     */
    @Override
    public void removeQuestion(ICreateQuestion<CreateFlashcardController> flashcard){
        questions.remove(flashcard);
        updateCreatedQuestions();
    }

    /**
     *  This method updates UI elements based on modifications of model
     */
    @Override
    public void update(){
        appliedTagBox.getChildren().clear();
        for (Subject subject : quiz.getTags()){
            Tag tag = new Tag(subject, quiz);
            appliedTagBox.getChildren().add(tag);
        }

        createTagsInPane();
    }

    /**
     * This method updates all CreateFlashCard controllers in scrollpane
     */
    private void updateCreatedQuestions(){
        items.getChildren().clear();
        for (CreateFlashcardController item : questions){
            items.getChildren().add(item);
        }

        flashcardScrollpane.setContent(items);
    }

    /**
     * This method is used to navigate to TagPane
     */
    @FXML
    public void navigateToTagPane() {
        if(isValid() && !quizName.getText().isEmpty()){
            tagPane.toFront();
        }
    }

    private boolean isValid(){
        for(CreateFlashcardController question : questions) {
            if(!question.isAbleToCreate()){
                return false;
            }
        }

        return true;
    }

    /**
     * This method adds all questions to the quiz and pushes the quiz to repository
     */
    @FXML
    private void createQuiz(){
        quiz.setName(quizName.getText());
        for (CreateFlashcardController question : questions) {
            if(question.isAbleToCreate()){
                IQuizable createdQuestion = question.createQuestion();
                quiz.addQuestion(createdQuestion);
            }
        }

        navigateToQuizCollection();
    }

    private void navigateToQuizCollection() {
        navigationStack.pushView(new HomeController());
    }

    /**
     * This method is used to navigate to the menu section of the page
     */
    @FXML
    private void navigateToMenu(){
        navigationStack.popView();
    }

    /**
     * This method is used to navigate to the flashcard section of the page
     */
    @FXML
    private void navigateToFlashcard(){
        flashcardPane.toFront();
    }

    /**
     * Initialize the tag component
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tagBox.setHgap(10);
        tagBox.setVgap(10);
        appliedTagBox.setSpacing(10);

        createTagsInPane();
    }

    /**
     * This method creates all tags to the tag component
     */
    private void createTagsInPane() {
        tagBox.getChildren().clear();
        for (Subject subject : Subject.values()){
            if (!quiz.getTags().contains(subject))
            tagBox.getChildren().add(new Tag(subject, quiz));
        }
    }

}
