package com.example.quizapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class takeQuizController implements Initializable {

    @FXML
    private Label QuizName;

    @FXML
    private AnchorPane QuizHolder;

    private Quiz quiz;

    public void setQuiz(Quiz quiz){
        this.quiz = quiz;
    }

    public void showNext(){
        quiz.nextQuestion();
        showQuestion();
    }

    public void showAnswer(){

    }

    public void showPrevious(){
        quiz.prevQuestion();
        showQuestion();

    }

    private void showQuestion() {
        AnchorPane pane = new AnchorPane();
        try {
            pane = FXMLLoader.load(getClass().getResource("multiChoice.fxml"));
        } catch (IOException e){
            System.out.println(e.getMessage());
        }

        QuizHolder.getChildren().setAll(pane);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showQuestion();
    }



}
