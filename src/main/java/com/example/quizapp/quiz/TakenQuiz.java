package com.example.quizapp.quiz;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TakenQuiz {

    private String quizId;
    private String userId;
    private int score;
    private LocalDateTime date;

    public TakenQuiz(String quizId, String userId, int score, LocalDateTime date) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.date = date;
    }

    public String getQuizId() {
        return quizId;
    }

    public String getUserId() {
        return userId;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
