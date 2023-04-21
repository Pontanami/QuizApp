package com.example.quizapp;

import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.model.Flashcard;
import com.example.quizapp.multiChoice.MultiChoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizTest {

    private Quiz quiz;

    @BeforeEach
    void setUp(){
        quiz = new Quiz("QuizTest");
    }

    @Test
    void getQuestions() {
        IQuizable<String> flashcard1 = new Flashcard("Water", "Element");
        IQuizable<String> flashcard2 = new Flashcard("Ice", "Cold");

        quiz.addQuestion(flashcard1);
        quiz.addQuestion(flashcard2);

        List<IQuizable<?>> questions = new ArrayList<>();
        questions.add(flashcard1);
        questions.add(flashcard2);

        assertArrayEquals(questions.toArray(), quiz.getQuestions().toArray());

    }

    @Test
    void getTags() {
        String tag = "BIOLOGY";
        assertTrue(quiz.addTag(Quiz.Subjects.valueOf(tag)));

        List<Quiz.Subjects> tags = quiz.getTags();
        assertEquals(1, tags.size());
        assertEquals(Quiz.Subjects.valueOf(tag), tags.get(0));
    }

    @Test
    void addTag() {
        String tag1 = "BIOLOGY";
        assertTrue(quiz.addTag(Quiz.Subjects.valueOf(tag1)));
        assertFalse(quiz.addTag(Quiz.Subjects.valueOf(tag1)));
        assertEquals(1, quiz.getTags().size());
    }

    @Test
    void removeTag() {
        String tag = "BIOLOGY";
        assertTrue(quiz.addTag(Quiz.Subjects.valueOf(tag)));
        assertTrue(quiz.removeTag(Quiz.Subjects.valueOf(tag)));
        assertEquals(0, quiz.getTags().size());
    }

    @Test
    void questionAddRemove(){
        IQuizable<String> flash = new Flashcard("QuestionOne", "AnswerOne");
        IQuizable<String> flashSecond = new Flashcard("QuestionTwo", "AnswerTwo");

        assertEquals(0, quiz.getQuestions().size());

        quiz.addQuestion(flash);
        quiz.addQuestion(flashSecond);

        assertEquals(2, quiz.getQuestions().size());

        quiz.nextQuestion();
        quiz.removeQuestion(1);

        assertEquals(1, quiz.getQuestions().size());
    }

    @Test
    void questionNavigationTests(){
        IQuizable<String> flash = new Flashcard("QuestionOne", "AnswerOne");
        IQuizable<String> flashSecond = new Flashcard("QuestionTwo", "AnswerTwo");

        Quiz quiz1 = new Quiz("navigation quiz");

        Assertions.assertThrows(IndexOutOfBoundsException.class, quiz1::nextQuestion);
        Assertions.assertThrows(IndexOutOfBoundsException.class, quiz1::getCurrentQuestion);
        Assertions.assertThrows(IndexOutOfBoundsException.class, quiz1::prevQuestion);

        quiz1.addQuestion(flash);
        Assertions.assertEquals(flash, quiz1.nextQuestion());
        Assertions.assertEquals(flash, quiz1.prevQuestion());

        quiz1.addQuestion(flashSecond);

        Assertions.assertEquals(flash, quiz1.getCurrentQuestion());
        Assertions.assertEquals(flashSecond, quiz1.prevQuestion());
        Assertions.assertEquals(flashSecond, quiz1.getCurrentQuestion());
        Assertions.assertEquals(flash, quiz1.prevQuestion());

        Assertions.assertEquals(flashSecond, quiz1.nextQuestion());
        Assertions.assertEquals(flashSecond, quiz1.getCurrentQuestion());
        Assertions.assertEquals(flash, quiz1.nextQuestion());

    }

    @Test
    void quizPointsTest(){
        Quiz quiz = new Quiz("points system test");

        Assertions.assertEquals(0, quiz.getPoints());
        quiz.addPoint();
        Assertions.assertEquals(1, quiz.getPoints());
    }
}