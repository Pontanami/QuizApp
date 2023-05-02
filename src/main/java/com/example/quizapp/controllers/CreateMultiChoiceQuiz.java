package com.example.quizapp.controllers;


import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.model.Subject;
import com.example.quizapp.Quiz;
import com.example.quizapp.interfaces.ICreateQuestion;
import com.example.quizapp.interfaces.IQuizManager;
import com.example.quizapp.user.FirebaseQuizRepository;
import com.example.quizapp.user.FirebaseUserRepository;
import com.example.quizapp.user.IQuizRepository;
import com.example.quizapp.user.IUserRepository;
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

public class CreateMultiChoiceQuiz extends AnchorPane implements IQuizManager<CreateMultichoice>, Initializable, IObserver {
    private AnchorPane rootpane;

    @FXML
    private AnchorPane tagPane;

    @FXML
    private AnchorPane flashcardPane;

    @FXML
    private VBox items;

    private List<CreateMultichoice> questions = new ArrayList<>();

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


    /**
     * Creates a CreateMultiChoiceQuiz
     * @param rootpane parent pane of the object
     */
    public CreateMultiChoiceQuiz(AnchorPane rootpane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createMultiChoiceQuiz.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.rootpane = rootpane;
        quiz.subscribe(this);
    }

    /**
     * This method adds a new CreateFlashcard controller to CreateFlashCardQuiz
     */
    @FXML
    @Override
    public void addQuestion(){
        CreateMultichoice multichoice = new CreateMultichoice(this);
        questions.add(multichoice);
        updateCreatedQuestions();
    }

    /**
     * This method removes a question controller from CreateFlashCardQuiz
     * @param multichoice to be removed
     */
    @Override
    public void removeQuestion(ICreateQuestion<CreateMultichoice> multichoice){
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
        tagPane.toFront();
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

        //TODO remove line below
        userRepository.loginUser("test", "test");
        quizRepository.uploadQuiz(quiz, userRepository.getCurrentUser());
    }

    /**
     * This method is used to navigate to the menu section of the page
     */
    @FXML
    private void navigateToMenu(){
        rootpane.getChildren().clear();
        rootpane.getChildren().add(new CreateQuiz(rootpane));
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
