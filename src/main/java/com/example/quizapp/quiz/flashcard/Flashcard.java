package com.example.quizapp.quiz.flashcard;

import com.example.quizapp.hints.IHint;
import com.example.quizapp.quiz.IQuizable;

public class Flashcard implements IQuizable<String> {

    private final String question;
    private final String answer;
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

    @Override
    public String getAnswer(){
        return answer;
    }

}
