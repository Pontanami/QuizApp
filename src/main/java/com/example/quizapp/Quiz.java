package com.example.quizapp;

import com.example.quizapp.interfaces.IQuizable;

import java.util.*;

public class Quiz {
    public final String name;
    private List<IQuizable> questions = new ArrayList<>();
    private List<Subjects> tags = new ArrayList<>();
    private int currentQuestionIndex = 0;

    public enum Subjects {
        Math,
        Science,
        Biology
    }

    public Quiz(String name){
        this.name = name;
    }

    public List<IQuizable> getQuestions(){
        return questions;
    }

    public List<Subjects> getTags() {
        return tags;
    }

    public boolean addTag(Subjects tag){
        if (!tags.contains(tag)) {
            tags.add(tag);
            return true;
        }
        return false;
    }

    public boolean removeTag(Subjects tag){
        return tags.remove(tag);
    }

    public void addQuestion(IQuizable question){
        questions.add(question);
    }

    public boolean removeQuestion(IQuizable question){
        return questions.remove(question);
    }

    public IQuizable getCurrentQuestion(){
        return questions.get(currentQuestionIndex);
    }

    public IQuizable nextQuestion(){
        if (currentQuestionIndex == 0){
            return getCurrentQuestion();
        }
        currentQuestionIndex = (currentQuestionIndex + 1) % questions.size();
        return getCurrentQuestion();
    }

    public IQuizable prevQuestion(){
        currentQuestionIndex = (currentQuestionIndex - 1) % questions.size();
        return getCurrentQuestion();
    }

}
