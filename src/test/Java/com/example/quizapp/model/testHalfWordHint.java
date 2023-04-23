package com.example.quizapp.model;

import com.example.quizapp.model.HalfWordHint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class testHalfWordHint {

    @Test
    public void testShowHint_evenNumberOfLetters(){
        HalfWordHint halfWordHint = new HalfWordHint("someAnswers1");
        Assertions.assertEquals("someAn", halfWordHint.showHint());
    }

    @Test
    public void testShowHint_oddNumberOfLetters(){
        HalfWordHint halfWordHint = new HalfWordHint("someAnswers12");
        Assertions.assertEquals("someAn", halfWordHint.showHint());
    }

}
