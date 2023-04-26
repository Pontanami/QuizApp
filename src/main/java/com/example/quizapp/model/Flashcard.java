package com.example.quizapp.model;

import com.example.quizapp.interfaces.IQuizable;

public class Flashcard implements IQuizable<String> {

    private String question;
    private String answer;
    private IHint<String> hint;

    /**
     * Creates a flashcard object, with a question and answer.
     * @param question flashcard question
     * @param answer answer to question
     */
    public Flashcard(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Creates a flashcard object, with a question, answer and a hint.
     * @param question flashcard question
     * @param answer answer to question
     * @param hint the type of hint that is used for the flashcard.
     */
    public Flashcard(String question, String answer, IHint hint) {
        this.question = question;
        this.answer = answer;
        this.hint = hint;
    }

    public IHint getWordHint() {
        return hint;
    }

    /**
     * Fetches different kinds of hints, depending on the hint type
     * @return hint to question
     */
    public String showHint(){
        if(hint != null)
            return hint.showHint();
        return "No hint available";
    }

    @Override
    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question){
        this.question = question;
    }

    @Override
    public String getAnswer(){
        return answer;
    }

    public void setAnswer(String answer){
        this.answer = answer;
    }
}
