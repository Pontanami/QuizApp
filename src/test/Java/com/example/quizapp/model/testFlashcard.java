package com.example.quizapp.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class testFlashcard {

    private static Flashcard flashcard;
    @BeforeAll
    public static void setup(){
        flashcard = new Flashcard("Is the ocean red", "no, the ocean is blue");
    }

    @Test
    public void testGetWordHint_withNoHint_shouldReturnNull(){
        Assertions.assertNull(flashcard.getWordHint());
    }

    @Test
    public void testGetWordHint_withNoHint_shouldReturnNotNull(){
        TextHint textHint = new TextHint("This is a hint");
        Flashcard flashcard1 = new Flashcard("someQuestion", "someAnswer", textHint);

        Assertions.assertNotNull(flashcard1);
    }

    @Test
    public void testShowHint(){
        TextHint textHint = new TextHint("This is a hint");
        Flashcard flashcard1 = new Flashcard("someQuestion", "someAnswer", textHint);

        Assertions.assertEquals("This is a hint", flashcard1.showHint());
    }


    @Test
    public void testGetQuestion(){
        Assertions.assertEquals("Is the ocean red", flashcard.getQuestion());
    }

    @Test
    public void testGetAnswer(){


        Assertions.assertEquals("no, the ocean is blue", flashcard.getAnswer());
    }

}
