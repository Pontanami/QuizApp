package com.example.quizapp.quiz.flashcard;

import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.firebase.IQuizRepository;
import com.example.quizapp.firebase.IUserRepository;
import com.example.quizapp.interfaces.IObservable;
import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.quiz.InputValidator;
import com.example.quizapp.quiz.Quiz;
import com.example.quizapp.quiz.tags.Subject;
import com.example.quizapp.quiz.tags.Tag;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class FlashCardQuizController extends AnchorPane implements IObserver, IObservable {

    @FXML
    private AnchorPane tagPane;

    @FXML
    private AnchorPane flashcardPane;

    @FXML
    private VBox items;

    @FXML
    private ScrollPane flashcardScrollpane;

    @FXML
    private FlowPane tagBox;

    @FXML
    private VBox appliedTagBox;

    @FXML
    private TextField quizName;

    private InputValidator validator = new InputValidator();
    protected IQuizRepository quizRepository = new FirebaseQuizRepository();
    protected IUserRepository userRepository = FirebaseUserRepository.getAuth();
    private NavigationStack navigationStack = NavigationStack.getInstance();

    private List<CreateFlashcardController> questions = new ArrayList<>();

    private List<IObserver> observers = new ArrayList<>();
    private Quiz quiz;

    public FlashCardQuizController(Quiz quiz){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createFlashcardQuiz.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.quiz = quiz;
        quizName.setText(quiz.getName());
        quiz.subscribe(this);
        validator.createValidationTextField(quizName);
        subscribe((IObserver) navigationStack.getSpecificView(HomeController.class));

        tagBox.setHgap(10);
        tagBox.setVgap(10);
        appliedTagBox.setSpacing(10);

        for (Subject subject : quiz.getTags()) {
            Tag tag = new Tag(subject, quiz);
            appliedTagBox.getChildren().add(tag);
        }

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

    protected void initQuizCreation(){
        quiz.setName(quizName.getText());
        quiz.getQuestions().clear();
        for (var item : questions) {
            var question = item.createQuestion();
            quiz.addQuestion(question);
        }
    }

    @FXML
    public void addQuestion(CreateFlashcardController flashCard){
        questions.add(flashCard);
        updateCreatedQuestions();
    }

    /**
     * This method removes a question controller from CreateFlashCardQuiz
     * @param flashcard to be removed
     */
    public void removeQuestion(CreateFlashcardController flashcard){
        questions.remove(flashcard);
        updateCreatedQuestions();
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
        quizRepository.uploadQuiz(quiz, userRepository.getCurrentUser());
        notifySubscribers();

        navigateToQuizCollection();
    }

    protected void navigateToQuizCollection() {
        navigationStack.popToRoot();
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
     *  This method updates UI elements based on modifications of model
     */
    @Override
    public void update(){
        appliedTagBox.getChildren().clear();

        for (Subject subject : quiz.getTags()) {
            Tag tag = new Tag(subject, quiz);
            appliedTagBox.getChildren().add(tag);
        }

        createTagsInPane();
    }

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        observers.remove((IObserver) observer);
    }

    @Override
    public void notifySubscribers() {
        for (IObserver observer : observers){
            observer.update();
        }
    }

    @FXML
    protected abstract void submitQuiz();
}
