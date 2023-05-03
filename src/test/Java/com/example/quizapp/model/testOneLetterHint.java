package com.example.quizapp.model;

import com.example.quizapp.hints.OneLetterHint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class testOneLetterHint {

    @Test
    public void testShowHint_calledOnce_shouldReturnFirstLetter(){
        OneLetterHint oneLetterHint = new OneLetterHint("answer");

        String hint = oneLetterHint.showHint();

        Assertions.assertEquals("a", hint);
    }

    @Test
    public void testShowHint_calledThreeTimes_shouldReturnThreeLetters(){
        OneLetterHint oneLetterHint = new OneLetterHint("answer");

        oneLetterHint.showHint();
        oneLetterHint.showHint();
        String hint = oneLetterHint.showHint();

        Assertions.assertEquals("ans", hint);
    }
}
