package com.example.quizapp.multiChoice;

import com.example.quizapp.model.IHint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EliminateChoiceHint implements IHint<List<String>> {

    List<String> choices;
    String correctChoice;

    /**
     * 
     * @param choices
     * @param correctChoice
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
