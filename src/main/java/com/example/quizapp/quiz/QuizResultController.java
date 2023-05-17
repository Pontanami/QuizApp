package com.example.quizapp.quiz;

import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.firebase.FirebaseTakenQuizRepository;
import com.example.quizapp.quiz.tags.Subject;
import com.example.quizapp.quiz.takeQuiz.TakeQuizController;
import com.example.quizapp.quiz.takeQuiz.TakenQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuiz;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.javatuples.Triplet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the results of a quiz
 */
public class QuizResultController extends AnchorPane {

    @FXML Label resultLabel;
    @FXML Label percentLabel;
    @FXML ScrollPane resultPane;
    @FXML VBox container;
    @FXML Label quizMean;
    @FXML Button retakeButton;

    private final String[] takenQuizAnswers;
    private final Quiz quiz;
    private final int points;
    private Quiz retakeQuiz;
    private final List<IQuizable<?>> questions;
    private String correctAnswer;
    private final int quizTotalPoints;
    NavigationStack navigationStack = NavigationStack.getInstance();

    /**
     *
     * Loads the QuizSummery fxml file and initialize values for the current object with the specified arguments
     * @param takenQuizAnswers the list of answers for the quiz questions
     * @param points the total points obtained
     * @param quiz the quiz that was taken
     */
    public QuizResultController(String[] takenQuizAnswers, int points, Quiz quiz, int quizTotalPoints){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/QuizSummery.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.takenQuizAnswers = takenQuizAnswers;
        this.quiz = quiz;
        this.points = points;
        this.quizTotalPoints = quizTotalPoints;
        questions = new ArrayList<>();
        resultPane.setFitToWidth(true);
        totalPoints(points, quizTotalPoints);
        allQuestions();
        populateRetakeQuiz();
        displayQuizMean(quiz.getId());
    }

    private void totalPoints(int score, int overAll){
        resultLabel.setText("Your total points for this quiz is: " + score + "/" + overAll);

        int grade = (score*100) / overAll;
        percentLabel.setText("Overall: " + grade + "%");
    }

    private void allQuestions(){

        for (int i = 0; i < takenQuizAnswers.length; i++){
            VBox questionBox = new VBox();
            Label questionLabel = new Label("Q " + (i+1) + ": " + quiz.getQuestions().get(i).getQuestion());

            String givenAnswer = takenQuizAnswers[i];
            correctAnswer = (String) quiz.getQuestions().get(i).getAnswer();
            Label answerLabel;
            if (givenAnswer != null && !givenAnswer.equals("")){
                if (givenAnswer.equals(correctAnswer)){
                    answerLabel = new Label("Your answer is: " + givenAnswer);
                }else {
                    answerLabel = new Label("Your answer is: " + givenAnswer +
                            ", but the correct answer is: " + correctAnswer);
                }
            }else {
                answerLabel = new Label("Not answered");
            }

            questionLabel.setId("questionLabel");
            answerLabel.setId("answeringLabel");

            questionBox.getChildren().addAll(questionLabel, answerLabel);

            style(givenAnswer, questionBox, questionLabel, answerLabel, i);

            container.getChildren().add(questionBox);
        }

        if (questions.size() == 0){
            retakeButton.setVisible(false);
        }
        resultPane.setContent(container);

    }

    private void style(String answer, VBox holder, Label q, Label ans, int number){
        holder.setMinWidth(100);
        holder.setMaxWidth(10000);
        holder.setPrefWidth(USE_COMPUTED_SIZE);
        holder.setMaxHeight(200);


        if (answer != null && answer.equals(correctAnswer) && !correctAnswer.equals("")){
            q.setId("correctQ");
            ans.setId("correctA");
            holder.setId("correctHolder");
        } else if (answer != null && !answer.equals("")){
            q.setId("incorrectQ");
            ans.setId("incorrectA");
            holder.setId("incorrectHolder");
            questions.add(quiz.getQuestions().get(number));
        } else {
            holder.setId("NoAnswer");
            questions.add(quiz.getQuestions().get(number));
        }
    }

    private void populateRetakeQuiz(){
        List<Subject> tags = new ArrayList<>(quiz.getTags());
        retakeQuiz = new Quiz(quiz.getName(), questions, tags, quiz.getId(), quiz.getCreatedBy(),
                questions.size(), quiz.getTotalAttempts());
    }

    /**
     * Navigates back to the home screen
     */
    public void navigateToHome(){
        navigationStack.popView();
        navigationStack.removeView(this);
    }


    /**
     * Navigates to take quiz screen with the new retake quiz
     */
    public void retake(){
        navigationStack.pushView(new TakeQuizController(retakeQuiz, points, quizTotalPoints));
        navigationStack.removeView(this);
    }

    private void displayQuizMean(String quizId){
        FirebaseQuizRepository quizRepository = new FirebaseQuizRepository();
        QuizQuery.QuizQueryBuilder query = new QuizQuery.QuizQueryBuilder().setId(quizId);

        Quiz quiz = quizRepository.getSingleQuiz(query);

        float mean = (float) quiz.getTotalPoints() / quiz.getTotalAttempts();

        quizMean.setText(String.format("Quiz mean: %.2f", mean));
    }
}
