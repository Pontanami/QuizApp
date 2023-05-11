package com.example.quizapp.quiz.takeQuiz;

import java.util.Date;


public class TakenQuiz {

    private String quizId;
    private String userId;
    private int score;
    private Date date;
    private String id;

    public TakenQuiz(String quizId, String userId, int score, Date date, String id) {
        this.quizId = quizId;
        this.userId = userId;
        this.score = score;
        this.date = date;
        this.id = id;
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
    public String getId() {
        return id;
    }
}
