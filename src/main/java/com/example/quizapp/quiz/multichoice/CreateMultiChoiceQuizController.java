package com.example.quizapp.quiz.multichoice;


import com.example.quizapp.NavigationStack;
import com.example.quizapp.interfaces.ICreateQuizObserver;
import com.example.quizapp.interfaces.IObservable;
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

public class CreateMultiChoiceQuizController extends AnchorPane implements IQuizManager<CreateMultichoiceController>, Initializable, IObserver, IObservable {
    private AnchorPane rootpane;

    @FXML
    private AnchorPane parentPane;

    @FXML
    private AnchorPane tagPane;

    @FXML
    private AnchorPane flashcardPane;

    @FXML
    private VBox items;

    private List<CreateMultichoiceController> questions = new ArrayList<>();

    @FXML
    private ScrollPane multichoiceScrollpane;

    @FXML
    private FlowPane tagBox;

    @FXML
    private VBox appliedTagBox;

    @FXML
    private TextField quizName;

    private Quiz quiz = new Quiz();

    private IQuizRepository quizRepository = new FirebaseQuizRepository();
    private IUserRepository userRepository = FirebaseUserRepository.getAuth();

    private NavigationStack navigation = NavigationStack.getInstance();

    private InputValidator validator = new InputValidator();

    private List<IObserver> observers = new ArrayList<>();

    /**
     * Creates a CreateMultiChoiceQuiz
     */
    public CreateMultiChoiceQuizController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createMultiChoiceQuiz.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        quiz.subscribe(this);
        validator.createValidationTextField(quizName);
        subscribe((IObserver) navigation.getSpecificView(HomeController.class));
    }

    /**
     * This method adds a new CreateFlashcard controller to CreateFlashCardQuiz
     */
    @FXML
    @Override
    public void addQuestion(){
        CreateMultichoiceController multichoice = new CreateMultichoiceController(this);
        questions.add(multichoice);
        updateCreatedQuestions();
    }

    /**
     * This method removes a question controller from CreateFlashCardQuiz
     * @param multichoice to be removed
     */
    @Override
    public void removeQuestion(ICreateQuestion<CreateMultichoiceController> multichoice){
        questions.remove(multichoice);
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
     * This method updates all CreateMultiChoice controllers in scrollpane
     */
    private void updateCreatedQuestions(){
        items.getChildren().clear();
        for (var item : questions){
            items.getChildren().add(item);
        }

        multichoiceScrollpane.setContent(items);
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
        for(CreateMultichoiceController question : questions) {
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
        for (var item : questions) {
            var question = item.createQuestion();
            quiz.addQuestion(question);
        }
        quizRepository.uploadQuiz(quiz, userRepository.getCurrentUser());
        notifySubscribers();
        navigateToQuizCollection();
    }

    private void navigateToQuizCollection() {
        navigation.popToRoot();
    }

    /**
     * This method is used to navigate to the menu section of the page
     */
    @FXML
    private void navigateToMenu(){
       navigation.popView();
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

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        observers.remove((ICreateQuizObserver) observer);
    }

    @Override
    public void notifySubscribers() {
        for (IObserver observer : observers){
            observer.update();
        }
    }
}
