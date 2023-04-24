package com.example.quizapp.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class CreateQuiz extends AnchorPane{

    @FXML
    AnchorPane rootpane;

   /* public CreateQuiz(AnchorPane rootpane){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("createQuiz.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.rootpane = rootpane;
    }*/

    @FXML
    public void navigateToCreateFlashcard() {
        rootpane.getChildren().clear();
        rootpane.getChildren().add(new CreateFlashCardQuiz(rootpane));
    }
}
