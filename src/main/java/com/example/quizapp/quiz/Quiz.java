package com.example.quizapp.quiz;

import com.example.quizapp.interfaces.IObservable;
import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.quiz.tags.Subject;

import java.util.*;

public class Quiz implements IObservable {
    private List<IObserver> observers = new ArrayList<>();
    private String name;
    private List<IQuizable<?>> questions = new ArrayList<>();
    private List<Subject> tags = new ArrayList<>();
    private String id, createdBy;



    /**
     * A class that includes a collection of questions of the type {@link IQuizable}
     */
    public Quiz(){
    }

    /**
     * @param name The name to be given for the quiz
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * A class that includes a collection of questions of the type {@link IQuizable}
     * @param name The quiz's name
     * @param questions The list of questions to be included in the quiz. All of the type {@link IQuizable}
     * @param tags The list of tags of the enum type {@link Subject}
     * @param id The unique id of this specific quiz
     * @param createdBy The user creating the quiz
     */
    public Quiz(String name, List<IQuizable<?>> questions, List<Subject> tags, String id, String createdBy){
        this.name = name;
        this.questions = questions;
        this.tags = tags;
        this.id = id;
        this.createdBy = createdBy;
    }

    /**
     * @return The name of the quiz
     */
    public String getName(){
        return name;
    }

    /**
     * @return The list of all {@link IQuizable} questions
     */
    public List<IQuizable<?>> getQuestions(){
        return questions;
    }

    /**
     * @return The list of all {@link Subject} tags
     */
    public List<Subject> getTags() {
        return tags;
    }

    /**
     * Adds a {@link Subject} to the quiz's list of tags.
     * @param tag The {@link Subject} to add.
     * @return true if the {@link Subject} isn't already in the list of tags, false otherwise
     */
    public boolean addTag(Subject tag){
        if (!tags.contains(tag)) {
            tags.add(tag);
            return true;
        }
        return false;
    }

    /**
     * Removes a {@link Subject} from the quiz's list of tags.
     * @param tag The {@link Subject} to remove.
     * @return true if the {@link Subject} is in the list of tags, false otherwise
     */
    public boolean removeTag(Subject tag){
        boolean isRemoved = tags.remove(tag);
        return isRemoved;
    }

    /**
     * Offers a toggle functionality of removing and adding a certain tag to the quiz.
     * @param tag The {@link Subject} to add or remove.
     */
    public void tagSelected(Subject tag){
        if (tags.contains(tag)){
            tags.remove(tag);
        }
        else {
            tags.add(tag);
        }
        notifySubscribers();
    }

    /**
     * Adds a {@link IQuizable} question from the quiz.
     * @param question The {@link IQuizable} object to add to the quiz
     */
    public void addQuestion(IQuizable<?> question){
        questions.add(question);
    }

    /**
     * Removes a {@link IQuizable} question from the quiz.
     * @param question The {@link IQuizable} object to remove
     */
    public void removeQuestion(IQuizable question){
        questions.remove(question);
    }

    /**
     * Removes a {@link IQuizable} question from the quiz.
     * @param index The index of question to remove
     */
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

    /**
     * @return The unique id of the quiz
     */
    public String getId() {
        return id;
    }

    /**
     * @return The user that this quiz was created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

}
