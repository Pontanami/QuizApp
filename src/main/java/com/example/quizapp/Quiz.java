package com.example.quizapp;

import com.example.quizapp.interfaces.IObservable;
import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.model.Subject;

import java.util.*;

public class Quiz implements IObservable {
    private List<IObserver> observers = new ArrayList<>();
    private String name;
    private List<IQuizable<?>> questions = new ArrayList<>();
    private List<Subject> tags = new ArrayList<>();


    /**
     * A class that includes a collection of questions of the type IQuizable
     */
    public Quiz(){
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public List<IQuizable<?>> getQuestions(){
        return questions;
    }

    public List<Subject> getTags() {
        return tags;
    }

    public boolean addTag(Subject tag){
        if (!tags.contains(tag)) {
            tags.add(tag);
            notifySubscribers();
            return true;
        }
        return false;
    }

    public boolean removeTag(Subject tag){
        boolean isRemoved = tags.remove(tag);
        notifySubscribers();
        return isRemoved;
    }

    public void addQuestion(IQuizable<?> question){
        questions.add(question);
    }

    public void removeQuestion(int index){
        if (index >= 0 && index < questions.size()) {
            questions.remove(index);
        }
    }




    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySubscribers() {
        for(IObserver observer : observers){
            observer.update();
        }
    }
}
