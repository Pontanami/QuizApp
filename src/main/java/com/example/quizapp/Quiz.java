package com.example.quizapp;

import com.example.quizapp.interfaces.IQuizable;

import java.util.*;

public class Quiz {
    private final String name;
    private List<IQuizable<?>> questions = new ArrayList<>();
    private List<Subjects> tags = new ArrayList<>();

    public enum Subjects {
        MATH,
        SCIENCE,
        BIOLOGY,
        ENGLISH,
        HISTORY,
        PHYSICS,
        GEOGRAPHY,
        CHEMISTRY
    }

    /**
     * A class that includes a collection of questions of the type IQuizable
     * @param name A string that represents the quiz's name
     */
    public Quiz(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public List<IQuizable<?>> getQuestions(){
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

    public void addQuestion(IQuizable<?> question){
        questions.add(question);
    }

    public void removeQuestion(int index){
        if (index >= 0 && index < questions.size()) {
            questions.remove(index);
        }
    }

}
