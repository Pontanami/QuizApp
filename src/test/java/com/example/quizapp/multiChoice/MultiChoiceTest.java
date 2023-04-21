
package com.example.quizapp.multiChoice;

import com.example.quizapp.model.IHint;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

class MultiChoiceTest {

    @ParameterizedTest
    @CsvSource({"What is the water's freezing temp in C, 0, 212, 100, 32, 1",
                "What is the earth's diameter in km, 12352, 12742, 14576, 15667, 2"})
    void isCorrectlySetup(String questionEx, String answer1, String answer2, String answer3, String answer4) {
        //externalAns represents an answer that is not one of the four choices which is expected to throw and
        List<String> expectedChoices = new ArrayList<>();
        expectedChoices.add(answer1);
        expectedChoices.add(answer2);
        expectedChoices.add(answer3);
        expectedChoices.add(answer4);
        MultiChoice mul = new MultiChoice(questionEx, answer1, expectedChoices, new EliminateChoiceHint(expectedChoices, answer1));
        Assertions.assertArrayEquals(expectedChoices.toArray(), mul.getChoices().toArray());

        Assertions.assertEquals(answer1, mul.getAnswer());
        Assertions.assertEquals(questionEx, mul.getQuestion());
    }
}

