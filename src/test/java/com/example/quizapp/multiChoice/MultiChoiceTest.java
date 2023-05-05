
package com.example.quizapp.multiChoice;

import com.example.quizapp.hints.EliminateChoiceHint;
import com.example.quizapp.hints.IHint;
import com.example.quizapp.quiz.multichoice.MultiChoice;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

class MultiChoiceTest {

    @ParameterizedTest
    @CsvSource({"What is the water's freezing temp in C, 0, 212, 100, 32, 1",
                "What is the earth's diameter in km, 12352, 12742, 14576, 15667, 2"})
    void isCorrectlySetup(String questionEx, String answer1, String answer2, String answer3, String answer4) {
        List<String> expectedChoices = new ArrayList<>(){
            {
                add(answer1);
                add(answer2);
                add(answer3);
                add(answer4);
            }
        };

        MultiChoice mul = new MultiChoice(questionEx, answer1, expectedChoices, new EliminateChoiceHint(expectedChoices, answer1));
        MultiChoice mul1 = new MultiChoice(questionEx, answer1, expectedChoices);

        Assertions.assertArrayEquals(expectedChoices.toArray(), mul.getChoices().toArray());

        Assertions.assertEquals(answer1, mul.getAnswer());
        Assertions.assertEquals(questionEx, mul.getQuestion());

        List<String> hint = mul.showHint();
        Assertions.assertEquals(mul.getChoices().size()/2, hint.size());

        int counter = 0;
        for (String choice: hint){
            if (mul.getChoices().contains(choice)){
                counter++;
            }
        }
        Assertions.assertEquals(mul.getChoices().size()/2, counter);
        Assertions.assertNotNull(mul.getHint());
        Assertions.assertNull(mul1.getHint());
    }
}

