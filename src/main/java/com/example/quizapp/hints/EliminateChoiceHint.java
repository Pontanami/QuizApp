package com.example.quizapp.hints;

import com.example.quizapp.hints.IHint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EliminateChoiceHint implements IHint<List<String>> {

    private final List<String> choices;
    private final String correctChoice;

    /**
     * @param choices The list of all choices that choices are going to get eliminated from.
     * @param correctChoice The answer that represents the correct choice. Should be an element in the list.
     */
    public EliminateChoiceHint(List<String> choices, String correctChoice){
        this.choices = choices;
        this.correctChoice = correctChoice;
    }

    @Override
    public List<String> showHint() {
        List<String> hintList = new ArrayList<>();
        hintList.add(correctChoice);
        List<String> remainingChoices = new ArrayList<>(choices);
        remainingChoices.remove(correctChoice);
        String randomChoice = remainingChoices.get(new Random().nextInt(remainingChoices.size()));
        hintList.add(randomChoice);
        Collections.shuffle(hintList);
        return hintList;
    }
}
