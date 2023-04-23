package com.example.quizapp.model;

import com.example.quizapp.model.TextHint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

public class testTextHint {

    @Test
    public void test(){
        TextHint textHint = new TextHint("Some hint to the question");

        String hint = textHint.showHint();

        Assertions.assertEquals("Some hint to the question", hint);
    }
}
