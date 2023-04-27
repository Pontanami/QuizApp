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
    private Set<Subject> tags = new HashSet<>();
    private int currentQuestionIndex = 0;
    private int points = 0;

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

    public Set<Subject> getTags() {
        return tags;
    }

    public boolean addTag(Subject tag){
        if (!tags.contains(tag)) {
            tags.add(tag);
            return true;
        }
        return false;
    }

    public boolean removeTag(Subject tag){
        boolean isRemoved = tags.remove(tag);
        return isRemoved;
    }

    public void tagSelected(Subject tag){
        if (tags.contains(tag)){
            tags.remove(tag);
        }
        else {
            tags.add(tag);
        }
        notifySubscribers();
    }

    public void addQuestion(IQuizable<?> question){
        questions.add(question);
    }

    public void removeQuestion(IQuizable question){
        questions.remove(question);
    }

    public void removeQuestion(int index){
        questions.remove(index);
        if (currentQuestionIndex > questions.size()-1){
            currentQuestionIndex = questions.size()-1;
        }
    }

    public IQuizable<?> getCurrentQuestion(){
        try {
            return questions.get(currentQuestionIndex);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("there is no questions to get");
        }
    }

    public IQuizable<?> nextQuestion(){
        if (isInvalidQuestionsSize()){
            return getCurrentQuestion();
        }
        currentQuestionIndex = Math.abs((currentQuestionIndex + 1) % questions.size());
        return getCurrentQuestion();
    }

    public IQuizable<?> prevQuestion(){
        if (isInvalidQuestionsSize()){
            return getCurrentQuestion();
        }
        currentQuestionIndex = Math.abs((currentQuestionIndex - 1) % questions.size());
        return getCurrentQuestion();
    }

    private boolean isInvalidQuestionsSize(){
        return (questions.size() == 1 || questions.size() == 0);
    }
    public void addPoint(){
        points++;
    }
    public int getPoints(){
        return points;
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
