package com.example.quizapp.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AddTags extends AnchorPane{

    private AnchorPane rootpane;

    public AddTags(AnchorPane rootpane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/AddTags.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.rootpane = rootpane;
    }
}
