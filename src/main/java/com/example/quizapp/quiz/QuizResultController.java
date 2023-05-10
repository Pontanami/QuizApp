package com.example.quizapp.quiz;

import com.example.quizapp.NavigationStack;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import org.javatuples.Triplet;

import java.io.IOException;

/**
 * Represents the results of a quiz
 */
public class QuizResultController extends AnchorPane {

    @FXML Label resultLabel;
    @FXML Label percentLabel;
    @FXML ScrollPane resultPane;
    @FXML VBox container;

    private final Triplet<String, String, Character>[] takenQuiz;
    NavigationStack navigationStack = NavigationStack.getInstance();

    /**
     *
     * Loads the QuizSummery fxml file and initialize values for the current object with the specified arguments
     * @param takenQuiz the quiz that was taken
     * @param points the total points obtained
     * @param fullScore the total score of the entire quiz
     */
    public QuizResultController(Triplet<String, String, Character>[] takenQuiz, int points, int fullScore){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/QuizSummery.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.takenQuiz = takenQuiz;

        resultPane.setPannable(true);
        resultPane.setFitToWidth(true);
        totalPoints(points, fullScore);
        allQuestions();
    }

    private void totalPoints(int score, int overAll){
        resultLabel.setText("Your total points for this quiz is: " + score + "/" + overAll);

        int grade = (score*100) / overAll;
        percentLabel.setText("Overall: " + grade + "%");
    }

    private void allQuestions(){
        int n = takenQuiz.length;

        for (int i = 0; i < n; i++){
            VBox questionBox = new VBox();
            Label questionLabel = new Label("Q " + (i+1) + ": " + takenQuiz[i].getValue0());

            String givenAnswer = takenQuiz[i].getValue1();
            Label answerLabel;
            if (!givenAnswer.equals("")){
                answerLabel = new Label("Your answer: " + takenQuiz[i].getValue1());
            }else {
                answerLabel = new Label("Not answered");
            }


            questionLabel.setFont(new Font("Arial", 48));
            answerLabel.setFont(new Font("Arial", 48));
            questionLabel.setPrefSize(1450, 106);
            answerLabel.setPrefSize(1450,94);
            questionLabel.setAlignment(Pos.CENTER);
            answerLabel.setAlignment(Pos.CENTER);

            questionBox.getChildren().addAll(questionLabel, answerLabel);

            style(questionBox, questionLabel, answerLabel, i);

            container.getChildren().add(questionBox);
        }


        resultPane.setContent(container);

    }

    private void style(VBox holder, Label q, Label ans, int number){
        char mark = takenQuiz[number].getValue2();
        if (mark == 'C'){
            holder.setStyle("-fx-border-width: 2px;");
            holder.setStyle("-fx-border-color: Green");
            q.setStyle("-fx-background-color: rgba(0, 255, 0, 0.5); -fx-background-insets: 0;");
            ans.setStyle("-fx-background-color: rgba(0, 255, 0, 0.5); -fx-background-insets: 0;");
        } else if (mark == 'F'){
            holder.setStyle("-fx-border-color: RED");
            q.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5); -fx-background-insets: 0;");
            ans.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5); -fx-background-insets: 0;");
        } else {
            holder.setStyle("-fx-border-width: 2px;");
            holder.setStyle("-fx-border-color: Black");
        }
    }

    /**
     * Navigates back to the home screen
     */
    public void navigateToHome(){
        navigationStack.removeView(this);
        navigationStack.popView();
    }



}
