package com.example.quizapp;

import com.example.quizapp.firebase.FirebaseTakenQuizRepository;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.quiz.takeQuiz.TakenQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuiz;
import com.example.quizapp.user.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.*;
import java.io.IOException;

public class LeaderboardController extends AnchorPane {
    private final FirebaseTakenQuizRepository takenQuizRepo = new FirebaseTakenQuizRepository();
    private final FirebaseUserRepository userRepo = FirebaseUserRepository.getAuth();
    @FXML
    VBox lbBox;

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
        TakenQuery.TakenQueryBuilder query = new TakenQuery.TakenQueryBuilder();
        List<TakenQuiz> takenQuizList = takenQuizRepo.getTakenQuizzes(query);
        List<User> users = userRepo.getUsers();

        List<Map.Entry<String, Integer>> sortedTotalScoresList = getTotalScores(takenQuizList, users, true);

        for (int i = 0; i < sortedTotalScoresList.size(); i++) {
            String username = sortedTotalScoresList.get(i).getKey();
            int totalScore = sortedTotalScoresList.get(i).getValue();
            lbBox.getChildren().add(new LeaderboardItemController(i+1 , username, totalScore));
        }

    }

    /**
     * Gets the total scores of all users and sorts them
     * @param takenQuizList the list of taken quizzes
     * @param users the list of users
     * @param sortDescending how to sort the result, true -> descending, false -> ascending
     * @return the list of total scores
     */
    private List<Map.Entry<String, Integer>> getTotalScores(List<TakenQuiz> takenQuizList, List<User> users, boolean sortDescending) {
        TreeMap<String, Integer> totalScoresMap;

        if (sortDescending) {
            totalScoresMap = new TreeMap<>();
        } else {
            totalScoresMap = new TreeMap<>(Collections.reverseOrder());
        }

        for (TakenQuiz takenQuiz : takenQuizList) {
            String userId = takenQuiz.getUserId();
            User user = users.stream()
                    .filter(u -> u.getId().equals(userId))
                    .findFirst()
                    .orElse(null);
            if (user == null) {
                continue;
            }
            String username = user.getName();
            int score = takenQuiz.getScore();
            totalScoresMap.put(username, totalScoresMap.getOrDefault(username, 0) + score);
        }

        return new ArrayList<>(totalScoresMap.entrySet());
    }


}
