package com.example.quizapp.quiz.takeQuiz;

import java.util.Date;


public class TakenQuiz {

    private String quizId;
    private String userId;
    private int score;
    private Date date;

    public TakenQuiz(String quizId, String userId, int score, Date date) {
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

    public Date getDate() {
        return date;
    }
}
