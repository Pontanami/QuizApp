package com.example.quizapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class LeaderboardItemController extends AnchorPane {

    @FXML
    Text posText, nameText, pointText;
    public LeaderboardItemController(int pos, String name, int points) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/LeaderboardItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
        posText.setText(pos + "");
        nameText.setText(name);
        pointText.setText(points + "");
    }
}
