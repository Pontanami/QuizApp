package com.example.quizapp.mainview;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class HomeController extends AnchorPane{

    @FXML
    BorderPane menuPane;

    @FXML
    AnchorPane parent;

    public HomeController(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        menuPane.setTop(new MenuController(menuPane));
    }
}
