
package com.example.quizapp.multiChoice;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Assertions;
import java.util.InputMismatchException;

class MultiChoiceTest {

    @ParameterizedTest
    @CsvSource({"What is the water's freezing temp in C, 0, 212, 100, 32, 1",
                "What is the earth's diameter in km, 12352, 12742, 14576, 15667, 2"})
    void isCorrectlySetup(String questionEx, String answer1, String answer2, String answer3, String answer4, String correct) {
        //externalAns represents an answer that is not one of the four choices which is expected to throw and
        String[] expectedChoices = new String[]{answer1, answer2, answer3, answer4, correct};
        MultiChoice mul = new MultiChoice(questionEx, expectedChoices);
        Assertions.assertArrayEquals(expectedChoices, mul.getAnswer());

        Assertions.assertEquals(5, mul.getAnswer().length);
        Assertions.assertEquals(questionEx, mul.getQuestion());
        Assertions.assertEquals(expectedChoices[4], mul.getAnswer()[4]);

        String[] falseCorrectAnswer = new String[]{answer1, answer2, answer3, answer4, "5"}; //5 is not a valid alternative
        Assertions.assertThrows(InputMismatchException.class, () -> new MultiChoice(questionEx, falseCorrectAnswer));
    }
}

