package com.example.quizapp.quiz.multichoice;

import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.hints.IHint;

import java.util.List;

public class MultiChoice implements IQuizable<Object> {
    private final List<String> choices;
    private final String question;
    private final String answer;
    private IHint<List<String>> hint;
    /**
     * Creates a {@link MultiChoice} question instance
     * @param question The question
     * @param answer The correct choice/answer
     * @param choices The choices
     */
    public MultiChoice(String question, String answer, List<String> choices){
        this.choices = choices;
        this.answer = answer;
        this.question = question;
    }
    /**
     * Creates a {@link MultiChoice} question instance
     * @param question The question
     * @param answer The correct choice/answer
     * @param choices The choices
     * @param hint A hint of the type {@link IHint}
     */
    public MultiChoice(String question, String answer, List<String> choices, IHint<List<String>> hint){
        this.choices = choices;
        this.answer = answer;
        this.question = question;
        this.hint = hint;
    }

    /**
     * @return The list of all given choices
     */
    public List<String> getChoices() {
        return choices;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public List<String> showHint() {
        return hint.showHint();
    }

    /**
     * @return The hint object
     */
    public IHint<List<String>> getHint() {
        return hint;
    }

    @Override
    public String getQuestion() {
        return question;
    }


}
