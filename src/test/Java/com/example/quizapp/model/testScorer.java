package com.example.quizapp.model;

import com.example.quizapp.model.Scorer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class testScorer {

    @Test
    public void testScoreQuestion_correctAnswer(){
        int userPoints = 10;

        userPoints = Scorer.scoreQuestion(userPoints, true);

        Assertions.assertEquals(11, userPoints);
    }

    @Test
    public void testScoreQuestion_incorrectAnswer(){
        int userPoints = 10;

        userPoints = Scorer.scoreQuestion(userPoints, false);

        Assertions.assertEquals(10, userPoints);
    }
}
