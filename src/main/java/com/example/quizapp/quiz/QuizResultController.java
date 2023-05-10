package com.example.quizapp.quiz;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import org.javatuples.Triplet;

import java.io.IOException;

public class QuizResultController extends AnchorPane {

    @FXML Label resultLabel;
    @FXML Label percentLabel;
    @FXML ScrollPane resultPane;
    @FXML VBox container;
    @FXML VBox takenQuizHolder;
    @FXML Label takenQuizQ;
    @FXML Label takenQuizA;


    private Triplet<String, String, Character>[] takenQuiz;
    private int points;
    private int numberOfQuestions;

    public QuizResultController(Triplet<String, String, Character>[] takenQuiz, int points, int total){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/QuizSummery.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.takenQuiz = takenQuiz;
        this.points = points;
        this.numberOfQuestions = total;

        resultPane.setPannable(true);
        totalPoints(points, total);
        allQuestions();
    }

    private void totalPoints(int score, int overAll){
        resultLabel.setText("Your total points for this quiz is: " + score + "/" + overAll);

        int grade = (score*100) / overAll;
        percentLabel.setText("Overall: " + grade + "%");
    }

    private void allQuestions(){
        int n = takenQuiz.length;
        takenQuizQ.setText("Q1: " + takenQuiz[0].getValue0());
        takenQuizA.setText("Your answer: " + takenQuiz[0].getValue1());

        style(takenQuizHolder, takenQuizQ, takenQuizA, 0);

        for (int i = 1; i < n; i++){
            VBox questionBox = new VBox();

            Label questionLabel = new Label("Q " + i + ": " + takenQuiz[i].getValue0());
            Label answerLabel = new Label("Your answer: " + takenQuiz[i].getValue1());

            questionLabel.setFont(new Font("Arial", 48));
            answerLabel.setFont(new Font("Arial", 48));
            questionLabel.setPrefSize(1450, 106);
            answerLabel.setPrefSize(1450,94);
            questionLabel.setAlignment(Pos.CENTER);
            //questionLabel.setTextAlignment(TextAlignment.CENTER);
            answerLabel.setAlignment(Pos.CENTER);
            //answerLabel.setTextAlignment(TextAlignment.CENTER);
            questionBox.getChildren().addAll(questionLabel, answerLabel);

            style(questionBox, questionLabel, answerLabel, i);

            container.getChildren().add(questionBox);
        }


        resultPane.setContent(container);

    }

    private void style(VBox holder, Label q, Label ans, int number){
        if (takenQuiz[number].getValue2().equals('C')){
            holder.setStyle("-fx-border-width: 2px;");
            holder.setStyle("-fx-border-color: Green");
            q.setStyle("-fx-background-color: rgba(0, 255, 0, 0.5); -fx-background-insets: 0;");
            ans.setStyle("-fx-background-color: rgba(0, 255, 0, 0.5); -fx-background-insets: 0;");
        } else {
            holder.setStyle("-fx-border-color: RED");
            q.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5); -fx-background-insets: 0;");
            ans.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5); -fx-background-insets: 0;");
        }
    }



}
