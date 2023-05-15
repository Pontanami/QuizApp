package com.example.quizapp;

import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class LeaderboardController {

    public LeaderboardController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Leaderboard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (
                IOException exception) {
            throw new RuntimeException(exception);
        }
    }

}
