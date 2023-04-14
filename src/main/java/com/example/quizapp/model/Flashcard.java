package com.example.quizapp.model;

public class Flashcard {

    private String question;
    private String answer;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion(){
        return question;
    }

    public String getAnswer(){
        return answer;
    }

}
