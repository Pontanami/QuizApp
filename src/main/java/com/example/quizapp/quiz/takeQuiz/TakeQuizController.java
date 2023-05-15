package com.example.quizapp.quiz.takeQuiz;

import com.example.quizapp.HelloApplication;
import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseTakenQuizRepository;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.interfaces.IObservable;
import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.mainview.HomeController;
import com.example.quizapp.mainview.MenuController;
import com.example.quizapp.quiz.Quiz;
import com.example.quizapp.quiz.QuizCollection;
import com.example.quizapp.quiz.QuizResultController;
import com.example.quizapp.quiz.flashcard.FlashCardController;
import com.example.quizapp.quiz.flashcard.Flashcard;
import com.example.quizapp.quiz.multichoice.MultiChoice;
import com.example.quizapp.quiz.multichoice.MultiChoiceController;
import com.example.quizapp.user.User;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.javatuples.Triplet;

/**
 * Represents the controller of one quiz that holds questions of either {@link Flashcard} or {@link MultiChoice} type.
 */
public class TakeQuizController extends AnchorPane implements IObservable {
    @FXML private Label quizName;
    @FXML private Button quizAnswer;
    @FXML private Button quizHint;
    @FXML private Button finishButton;
    @FXML private BorderPane quizHolder;
    @FXML private ProgressBar quizProgress;
    @FXML private Button quizNext;
    @FXML private Button quizPrevious;
    @FXML private Label quizPoints;
    @FXML private ImageView correct;
    @FXML private ImageView wrong;

    private final HashMap<String, AnchorPane> previousNodes = new HashMap<>();
    private final List<String> answeredQuestions = new ArrayList<>();
    private BigDecimal progress = new BigDecimal("0.0");
    private IAnswerable specificController;
    private final QuizAttempt quizAttempt;
    private final Quiz quiz;
    NavigationStack navigationStack = NavigationStack.getInstance();
    private List<IObserver> observers = new ArrayList<>();
    private final Triplet<String, String, String>[] takenQuiz;
    private int questionIndex = 0;

    /**
     * @param quiz The quiz to view/take
     */
    public TakeQuizController(Quiz quiz){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/takeQuiz.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.quiz = quiz;
        this.quizAttempt = new QuizAttempt(quiz);
        quizName.setText(quiz.getName());
        quizPrevious.setDisable(true);
        quizPoints.setText("Points: " + quizAttempt.getPoints() + "/" + quiz.getQuestions().size());
        int quizSize = quiz.getQuestions().size();
        takenQuiz = new Triplet[quizSize];
        showQuestion();
        subscribe((IObserver) navigationStack.getSpecificView(HomeController.class));
    }


    /**
     * Displays the next question according to the order specified in the given questions list.
     */
    public void showNext(){
        questionIndexController('n');
        quizAttempt.nextQuestion();
        switchNextAndFinishBtn();
        quizPrevious.setDisable(false);
        showQuestion();
        isAnswered();
    }

    private void switchNextAndFinishBtn() {
        if (quizAttempt.isQuizFinished()) {
            quizNext.setVisible(false);
            finishButton.setVisible(true);
        }
    }

    /**
     * Displays the previous question according to the order specified in the given questions list.
     */
    public void showPrevious(){
        questionIndexController('p');
        quizAttempt.prevQuestion();
        quizNext.setVisible(true);
        finishButton.setVisible(false);
        if(quizAttempt.getCurrentQuestionIndex() == 0){
            quizPrevious.setDisable(true);
        }
        showQuestion();
        isAnswered();
    }

    private void retrieveQuestion() {
        quizHolder.setCenter(previousNodes.get(quizAttempt.getCurrentQuestion().getQuestion()));
    }

    @FXML
    private void showButtons(){
        if (isFlashCardQuiz()){
            wrong.setVisible(true);
            correct.setVisible(true);
        }
    }

    /**
     * Calls the showHint() method from a controller of the type {@link IAnswerable}.
     * @see IAnswerable#showHint()
     */
    public void showHint(){
        specificController.showHint();
    }

    /**
     * Calls the revealAnswer() method from a controller of the type {@link IAnswerable}. Handles the point system
     * according to the response of the controller. Disables answer and hint buttons.
     * @see IAnswerable#revealAnswer()
     */
    public void disablePointButtons(){
        correct.setImage(new Image(String.valueOf(getClass().getResource("/img/like_disabled.png"))));
        wrong.setImage(new Image(String.valueOf(getClass().getResource("/img/like_dislike_disabled.png"))));
        quizHint.setDisable(true);
        answeredQuestions.add(quizAttempt.getCurrentQuestion().getQuestion());
    }

    @FXML
    private void correctAnswer(){

        if(!answeredQuestions.contains(quizAttempt.getCurrentQuestion().getQuestion())) {
            quizAttempt.addPoint();
            quizPoints.setText("Points: " + quizAttempt.getPoints() + "/" + quizAttempt.getQuiz().getQuestions().size());
            takenQuiz[questionIndex] = Triplet.with(takenQuiz[questionIndex].getValue0(), (String)quizAttempt.getCurrentQuestion().getAnswer(),
                    (String)quizAttempt.getCurrentQuestion().getAnswer());
            disablePointButtons();
        }
    }

    @FXML
    private void wrongAnswer(){
        if(!answeredQuestions.contains(quizAttempt.getCurrentQuestion().getQuestion())) {
            takenQuiz[questionIndex] = Triplet.with(takenQuiz[questionIndex].getValue0(), "Not correct answer",
                    "Correct Answer");
        }
        disablePointButtons();
    }

    private void isAnswered(){
        if(answeredQuestions.contains(quizAttempt.getCurrentQuestion().getQuestion())){
            correct.setImage(new Image(String.valueOf(getClass().getResource("/img/like_disabled.png"))));
            wrong.setImage(new Image(String.valueOf(getClass().getResource("/img/like_dislike_disabled.png"))));
        }
        else{
            correct.setImage(new Image(String.valueOf(getClass().getResource("/img/like.png"))));
            wrong.setImage(new Image(String.valueOf(getClass().getResource("/img/like_dislike.png"))));
            wrong.setVisible(false);
            correct.setVisible(false);
        }

        quizAnswer.setDisable(answeredQuestions.contains(quizAttempt.getCurrentQuestion().getQuestion()));
    }

    public void showAnswer(){
        if (specificController.revealAnswer()){
            quizAttempt.addPoint();
            quizPoints.setText("Points: " + quizAttempt.getPoints() + "/" + quizAttempt.getQuiz().getQuestions().size());
        }
        quizAnswer.setDisable(true);
        quizHint.setDisable(true);
        takenQuiz[questionIndex] = Triplet.with(takenQuiz[questionIndex].getValue0(), specificController.usersAnswer(),
                (String)quizAttempt.getCurrentQuestion().getAnswer());

        answeredQuestions.add(quizAttempt.getCurrentQuestion().getQuestion());
        increaseProgress();
    }

    /**
     * Checks if the quiz is a {@link Flashcard} quiz.
     * @return true if the quiz is a {@link Flashcard} quiz, false otherwise.
     */
    private boolean isFlashCardQuiz() {
        return quizAttempt.getQuiz().getQuestions().get(0) instanceof Flashcard;
    }

    /**
     * Checks if the quiz is a {@link MultiChoice} quiz.
     * @return true if the quiz is a {@link MultiChoice} quiz, false otherwise.
     */
    private boolean isMultiChoiceQuiz() {
        return quizAttempt.getQuiz().getQuestions().get(0) instanceof MultiChoice;
    }

    private void showQuestion() {
        switchNextAndFinishBtn();
        if (answeredQuestions.contains(quizAttempt.getCurrentQuestion().getQuestion())){
            retrieveQuestion();
            quizHint.setDisable(true);
        } else {
            takenQuiz[questionIndex] = new Triplet<String, String, String>(quizAttempt.getCurrentQuestion().getQuestion(),
                    "", "");
            quizHint.setDisable(false);
            AnchorPane pane = new AnchorPane();
            try {
                if (isMultiChoiceQuiz()) {
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("multiChoice.fxml"));
                    pane = fxmlLoader.load();
                    wrong.setVisible(false);
                    correct.setVisible(false);
                    MultiChoiceController controller = fxmlLoader.getController();
                    controller.initializeData((MultiChoice) quizAttempt.getCurrentQuestion());
                    specificController = controller;
                } else if (isFlashCardQuiz()) {
                    quizAnswer.setVisible(false);
                    wrong.setVisible(false);
                    correct.setVisible(false);
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FlashCard.fxml"));
                    pane = fxmlLoader.load();

                    FlashCardController controller = fxmlLoader.getController();
                    controller.initializeData((Flashcard) quizAttempt.getCurrentQuestion());
                    specificController = controller;
                }
                previousNodes.put(quizAttempt.getCurrentQuestion().getQuestion(), pane);
                quizHolder.setCenter(pane);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    /**
     * increases the progress bar by one step. The size of the step depends on the total number of questions a quiz has.
     */
    public void increaseProgress(){
        int numberOfQuestions = quizAttempt.getQuiz().getQuestions().size();
        double progressStep = 1.0 / numberOfQuestions;

        progress = progress.setScale(2, RoundingMode.HALF_EVEN);

        if (progress.doubleValue() < 1){
            progress = new BigDecimal(Double.toString(progress.doubleValue() + progressStep));
            quizProgress.setProgress(progress.doubleValue());
        }
    }

    /**
     * Navigate back to the given {@link QuizCollection} when instantiating.
     */
    public void finish(){
        FirebaseTakenQuizRepository qr = new FirebaseTakenQuizRepository();
        User currentuser = FirebaseUserRepository.getAuth().getCurrentUser();
        qr.uploadTakenQuiz(quizAttempt.getQuiz().getId(), currentuser.getId(), quizAttempt.getPoints());
        notifySubscribers();
       navigationStack.pushView(new QuizResultController(takenQuiz, quizAttempt.getPoints(),
                quizAttempt.getQuiz().getQuestions().size()));
        navigationStack.removeView(this);
    }

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySubscribers() {
        for (IObserver observer : observers)
            observer.update();

    }
    private void questionIndexController(Character c){
        if (c.equals('n')){
            questionIndex++;
        }else {
            if (questionIndex > 0){
                questionIndex--;
            }
        }
    }
}
