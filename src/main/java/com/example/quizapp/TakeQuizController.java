package com.example.quizapp;

import com.example.quizapp.multiChoice.MultiChoice;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TakeQuizController{

    @FXML
    private Label QuizName;

    @FXML
    private BorderPane QuizHolder;

    private Quiz quiz;

    public void initializeData(Quiz quiz){
        this.quiz = quiz;
        showQuestion();

    }

    public void showNext(){
        quiz.nextQuestion();
        showQuestion();
    }

    public void showPrevious(){
        quiz.prevQuestion();
        showQuestion();
    }

    private void showQuestion() {
        AnchorPane pane = new AnchorPane();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("multiChoice.fxml"));
            pane = fxmlLoader.load();
            MultiChoiceController controller = fxmlLoader.getController();
            controller.initializeData((MultiChoice) quiz.getCurrentQuestion());

        } catch (IOException e){
            e.printStackTrace();
        }

        QuizHolder.setCenter(pane);
    }



}
