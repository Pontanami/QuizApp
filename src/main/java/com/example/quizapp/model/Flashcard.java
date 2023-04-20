package com.example.quizapp.model;

public class Flashcard implements IQuestion{

    private String question;
    private String answer;
    private IHint<String> wordHint;

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
     * @param wordHint the type of hint that is used for the flashcard.
     */
    public Flashcard(String question, String answer, IHint wordHint) {
        this.question = question;
        this.answer = answer;
        this.wordHint = wordHint;
    }

    public IHint getWordHint() {
        return wordHint;
    }

    /**
     * Fetches different kinds of hints, depending on the hint type
     * @return hint to question
     */
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
