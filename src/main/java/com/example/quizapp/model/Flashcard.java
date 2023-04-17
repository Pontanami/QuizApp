package com.example.quizapp.model;

public class Flashcard implements IQuestion{

    private String question;
    private String answer;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    @Override
    public String getQuestion(){
        return question;
    }

    @Override
    public String getAnswer(){
        return answer;
    }
}
