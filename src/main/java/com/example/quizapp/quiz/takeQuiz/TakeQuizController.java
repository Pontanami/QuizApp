package com.example.quizapp.quiz.takeQuiz;

import com.example.quizapp.HelloApplication;
import com.example.quizapp.quiz.Quiz;
import com.example.quizapp.quiz.QuizCollection;
import com.example.quizapp.quiz.multichoice.MultiChoiceController;
import com.example.quizapp.quiz.flashcard.FlashCardController;
import com.example.quizapp.quiz.flashcard.Flashcard;
import com.example.quizapp.quiz.multichoice.MultiChoice;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Represents the controller of one quiz that holds questions of either {@link Flashcard} or {@link MultiChoice} type.
 * The method initializeData() should be called. One of the methods setAsFlashCardQuiz() or setAsMultiChoiceQuiz()
 * should also be called when initializing the controller.
 * @see TakeQuizController#initializeData(QuizAttempt)
 * @see TakeQuizController#setAsFlashCardQuiz()
 * @see TakeQuizController#setAsMultiChoiceQuiz()
 */
public class TakeQuizController extends AnchorPane {
    @FXML private Label quizName;
    @FXML private Button quizAnswer;
    @FXML private Button quizHint;
    @FXML private Button finishButton;
    @FXML private BorderPane quizHolder;
    @FXML private ProgressBar quizProgress;
    @FXML private Button quizNext;
    @FXML private Button quizPrevious;
    @FXML private Label quizPoints;

    private final HashMap<String, AnchorPane> previousNodes = new HashMap<>();
    private final List<String> answeredQuestions = new ArrayList<>();
    private BigDecimal progress = new BigDecimal("0.0");
    private IAnswerable specificController;
    private boolean isMultiChoice = false;
    private boolean isFlashCard = false;
    private int points = 0;
    private QuizAttempt quiz;

    @FXML
    private AnchorPane parentPane;

    /**
     * @param parentPane The {@link AnchorPane} that is going to include all interchanging {@link com.example.quizapp.quiz.IQuizable} questions.
     */
    public TakeQuizController(AnchorPane parentPane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/takeQuiz.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentPane = parentPane;
    }

    /**
     * Establishes the type of questions withing the quiz as {@link Flashcard}.
     */
    public void setAsFlashCardQuiz(){
        isFlashCard = true;
        isMultiChoice = false;
    }

    /**
     * Establishes the type of questions withing the quiz as {@link MultiChoice}.
     */
    public void setAsMultiChoiceQuiz(){
        isMultiChoice = true;
        isFlashCard = false;
    }

    /**
     * Initializes the values needed when starting a quiz attempt.
     * @param quiz The {@link QuizAttempt} instance needed to navigate the attempt.
     */
    public void initializeData(QuizAttempt quiz){
        this.quiz = quiz;
        if (quiz.getCurrentQuestion() instanceof Flashcard) {
            setAsFlashCardQuiz();
        } else {
            setAsMultiChoiceQuiz();
        }
        quizName.setText(quiz.getQuiz().getName());
        quizPrevious.setDisable(true);
        quizPoints.setText("Points: " + points + "/" + quiz.getQuiz().getQuestions().size());
        showQuestion();
    }

    /**
     * Displays the next question according to the order specified in the given questions list.
     */
    public void showNext(){
        quiz.nextQuestion();
        switchNextAndFinishBtn();
        quizPrevious.setDisable(false);
        showQuestion();
        isAnswered();
    }

    private void switchNextAndFinishBtn() {
        if (quiz.isQuizFinished()) {
            quizNext.setVisible(false);
            finishButton.setVisible(true);
        }
    }

    /**
     * Displays the previous question according to the order specified in the given questions list.
     */
    public void showPrevious(){
        quiz.prevQuestion();
        quizNext.setVisible(true);
        finishButton.setVisible(false);
        if(quiz.getCurrentQuestionIndex() == 0){
            quizPrevious.setDisable(true);
        }
        showQuestion();
        isAnswered();
    }

    private void retrieveQuestion() {
        quizHolder.setCenter(previousNodes.get(quiz.getCurrentQuestion().getQuestion()));
    }
    /**
     * Calls the showHint() method from a controller of the type {@link IAnswerable}.
     * @see IAnswerable#showHint()
     */
    public void showHint(){
        if (quizAnswer.isDisabled()){
            quizHint.setDisable(true);
        } else {
            specificController.showHint();
            quizHint.setDisable(true);
        }
    }

    /**
     * Calls the revealAnswer() method from a controller of the type {@link IAnswerable}. Handles the point system
     * according to the response of the controller. Disables answer and hint buttons.
     * @see IAnswerable#revealAnswer()
     */
    public void showAnswer(){
        if (specificController.revealAnswer()){
            points++;
            quizPoints.setText("Points: " + points + "/" + quiz.getQuiz().getQuestions().size());
        }
        quizAnswer.setDisable(true);
        quizHint.setDisable(true);
        answeredQuestions.add(quiz.getCurrentQuestion().getQuestion());
    }

    private void isAnswered(){
        quizAnswer.setDisable(answeredQuestions.contains(quiz.getCurrentQuestion().getQuestion()));
    }

    private void showQuestion() {
        //quiz.getCurrentQuestion().getQuestion().equals(quiz.getQuiz().getQuestions().get(quiz.getQuiz().getQuestions().size()-1).getQuestion())
        switchNextAndFinishBtn();
        if (answeredQuestions.contains(quiz.getCurrentQuestion().getQuestion())){
            retrieveQuestion();
            quizHint.setDisable(true);
        } else {
            quizHint.setDisable(false);
            AnchorPane pane = new AnchorPane();
            try {
                if (isMultiChoice) { //Maybe do a check with instanceOF?
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("multiChoice.fxml"));
                    pane = fxmlLoader.load();

                    MultiChoiceController controller = fxmlLoader.getController();
                    controller.initializeData((MultiChoice) quiz.getCurrentQuestion());
                    specificController = controller;
                } else if (isFlashCard){ //Maybe do a check with instanceOF?
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("FlashCard.fxml"));
                    pane = fxmlLoader.load();

                    FlashCardController controller = fxmlLoader.getController();
                    controller.initializeData((Flashcard) quiz.getCurrentQuestion());
                    specificController = controller;
                }
                previousNodes.put(quiz.getCurrentQuestion().getQuestion(), pane);
                increaseProgress();
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
        int numberOfQuestions = quiz.getQuiz().getQuestions().size();
        double progressStep = 1.0 / numberOfQuestions;

        progress = progress.setScale(2, RoundingMode.HALF_EVEN);

        if (progress.doubleValue() < 1){
            progress = new BigDecimal(Double.toString(progress.doubleValue() + progressStep));
            quizProgress.setProgress(progress.doubleValue());
        }
    }

    /**
     * Go back to the {@link QuizCollection} That was given when creating this instance of {@link TakeQuizController}.
     */
    public void finish(){
        QuizCollection quizCollection = new QuizCollection(parentPane);
        parentPane.getChildren().clear();
        parentPane.getChildren().add(quizCollection);
    }

}
