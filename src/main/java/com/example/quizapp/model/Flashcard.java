package com.example.quizapp.model;

import com.example.quizapp.interfaces.IQuizable;

public class Flashcard implements IQuizable<String> {

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
