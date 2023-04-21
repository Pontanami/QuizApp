package com.example.quizapp;

import com.example.quizapp.interfaces.IQuizable;

import java.util.*;

public class Quiz {
    public final String name;
    private List<IQuizable<?>> questions = new ArrayList<>();
    private List<Subjects> tags = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int points = 0;

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

    /**
     *
     * @param index
     */
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

}
