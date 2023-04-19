package com.example.quizapp.model;

public class Flashcard implements IQuestion{

    private String question;
    private String answer;
    private IHint<String> wordHint;

    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Flashcard(String question, String answer, IHint wordHint) {
        this.question = question;
        this.answer = answer;
        this.wordHint = wordHint;
    }

    public IHint getWordHint() {
        return wordHint;
    }

    public String showHint(){
        return wordHint.showHint();
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
