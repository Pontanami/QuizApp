package com.example.quizapp.model;

import com.example.quizapp.quiz.flashcard.FlashcardValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class testFlashcardValidator {

    private FlashcardValidator validator = new FlashcardValidator();

    @Test
    public void testValidate_shouldReturnTrue(){
        Assertions.assertTrue(validator.validate("1"));
    }

    @Test
    public void testValidate_shouldReturnFalse(){
        Assertions.assertFalse(validator.validate("0"));
    }
}
