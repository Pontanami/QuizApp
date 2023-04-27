package com.example.quizapp;

import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.model.Flashcard;
import com.example.quizapp.model.OneLetterHint;
import com.example.quizapp.model.Subject;
import com.example.quizapp.model.TextHint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class TestQuizSearch {

    Flashcard flashcard1 = new Flashcard("What the capital of Sweden?", "Stockholm", new OneLetterHint("Stockholm"));
    Flashcard flashcard2 = new Flashcard("What is 1+1", "2", new TextHint("ett plus ett är två"));
    List<IQuizable<?>> questions = List.of(flashcard1, flashcard2);
    Quiz quiz = new Quiz("Quiz1", questions, Set.of(Subject.Mathematics, Subject.Economics), "1", "user1");
    Quiz quiz2 = new Quiz("ABC", questions, Set.of(Subject.Mathematics, Subject.Economics), "1", "user1");
    Quiz quiz3 = new Quiz("Quiz3", questions, Set.of(Subject.Mathematics, Subject.Economics), "1", "user1");

    List<Quiz> quizzes;

    @BeforeEach
    public void setup(){
       quizzes = List.of(quiz, quiz2, quiz3);
    }
    @Test
    public void testQuizSearch_ShouldReturn_2_Quizzes(){
        Assertions.assertEquals(2, QuizSearch.search(quizzes, "quiz").size());
    }

    @Test
    public void testQuizSearch_ShouldReturn_1_Quiz(){
        Assertions.assertEquals(1, QuizSearch.search(quizzes, "abc").size());
    }

    @Test
    public void testQuizSearch_ShouldReturn_0_Quizzes(){
        Assertions.assertEquals(0, QuizSearch.search(quizzes, "def").size());
    }

}
