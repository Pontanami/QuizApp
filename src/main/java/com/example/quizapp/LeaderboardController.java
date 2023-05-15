package com.example.quizapp;

import com.example.quizapp.firebase.FirebaseTakenQuizRepository;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.quiz.takeQuiz.TakenQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuiz;
import com.example.quizapp.user.User;
import javafx.fxml.FXMLLoader;

import java.util.*;
import java.io.IOException;

public class LeaderboardController {
    private final FirebaseTakenQuizRepository takenQuizRepo = new FirebaseTakenQuizRepository();
    private final FirebaseUserRepository userRepo = FirebaseUserRepository.getAuth();

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
        java.util.List<TakenQuiz> takenQuizList = takenQuizRepo.getTakenQuizzes(query);
        java.util.List<User> users = userRepo.getUsers();

        List<Map.Entry<String, Integer>> sortedTotalScoresList = getTotalScores(takenQuizList, users);

        /* Print out values
        for (Map.Entry<String, Integer> entry : sortedTotalScoresList) {
            String username = entry.getKey();
            int totalScore = entry.getValue();
            System.out.println(username + ": " + totalScore + " score");
        }

         */

    }

    private List<Map.Entry<String, Integer>> getTotalScores(List<TakenQuiz> takenQuizList, List<User> users) {
        TreeMap<String, Integer> totalScoresMap = new TreeMap<>();
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
        List<Map.Entry<String, Integer>> sortedTotalScoresList = new ArrayList<>(totalScoresMap.entrySet());
        return sortedTotalScoresList;
    }

}
