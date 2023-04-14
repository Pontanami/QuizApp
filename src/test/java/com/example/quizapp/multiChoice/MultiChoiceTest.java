package com.example.quizapp.multiChoice;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Assertions;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;

class MultiChoiceTest {
    private static MultiChoiceModel mul;
    Random random = new Random();

    @ParameterizedTest
    @CsvSource({"What is the water's freezing temp in C, non-answer, 0, 212, 100, 32",
                "What is the earth's diameter in km, 3, 12352, 12742, 14576, 15667", })
    void isCorrectlySetup(String questionEx, String externalAns, String answer1, String answer2, String answer3, String answer4) {
        //externalAns represents an answer that is not one of the four choices which is expected to throw and
        mul = new MultiChoiceModel(questionEx, answer1, answer2, answer3, answer4);
        ArrayList<String> expectedChoices = new ArrayList<String>(){
            {
                add(answer1);
                add(answer2);
                add(answer3);
                add(answer4);
            }
        };
        Assertions.assertArrayEquals(expectedChoices.toArray(), mul.getChoices().toArray());

        int ran = random.nextInt(0, 4);
        String ranVal = expectedChoices.get(ran);
        mul.setCorrectAnswer(ranVal);

        Assertions.assertEquals(questionEx, mul.getQuestion());
        Assertions.assertEquals(expectedChoices.get(ran), mul.getCorrectAnswer());
        Assertions.assertEquals(0, mul.getPoints()); //A better way?
        Assertions.assertThrows(InputMismatchException.class, () -> mul.setCorrectAnswer(externalAns));
    }
}











