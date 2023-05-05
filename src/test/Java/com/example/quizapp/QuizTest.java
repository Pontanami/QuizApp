package com.example.quizapp;

import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.quiz.flashcard.Flashcard;
import com.example.quizapp.quiz.multichoice.MultiChoice;
import com.example.quizapp.quiz.tags.Subject;
import com.example.quizapp.quiz.Quiz;
import com.example.quizapp.quiz.takeQuiz.QuizAttempt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QuizTest {

    private Quiz quiz;
    private QuizAttempt attempt;

    @BeforeEach
    void setUp(){
        quiz = new Quiz();
        attempt = new QuizAttempt(quiz);
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
        String tag = "Biology";
        assertTrue(quiz.addTag(Subject.valueOf(tag)));

        List<Subject> tags = quiz.getTags();
        assertEquals(1, tags.size());
        assertEquals(Subject.valueOf(tag), tags.get(0));
    }

    @Test
    void addTag() {
        String tag1 = "Biology";
        assertTrue(quiz.addTag(Subject.valueOf(tag1)));
        assertFalse(quiz.addTag(Subject.valueOf(tag1)));
        assertEquals(1, quiz.getTags().size());
    }

    @Test
    void removeTag() {
        String tag = "Biology";
        assertTrue(quiz.addTag(Subject.valueOf(tag)));
        assertTrue(quiz.removeTag(Subject.valueOf(tag)));
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

        attempt.nextQuestion();
        quiz.removeQuestion(1);
        quiz.removeQuestion(flashSecond);

        assertEquals(1, quiz.getQuestions().size());
    }

    @Test
    void questionNavigationTests(){
        IQuizable<String> flash = new Flashcard("QuestionOne", "AnswerOne");
        IQuizable<String> flashSecond = new Flashcard("QuestionTwo", "AnswerTwo");

        Assertions.assertThrows(IndexOutOfBoundsException.class, attempt::nextQuestion);
        Assertions.assertThrows(IndexOutOfBoundsException.class, attempt::getCurrentQuestion);
        Assertions.assertThrows(IndexOutOfBoundsException.class, attempt::prevQuestion);

        quiz.addQuestion(flash);
        Assertions.assertEquals(flash, attempt.nextQuestion());
        Assertions.assertEquals(flash, attempt.prevQuestion());

        quiz.addQuestion(flashSecond);

        Assertions.assertEquals(flash, attempt.getCurrentQuestion());
        Assertions.assertEquals(flashSecond, attempt.prevQuestion());
        Assertions.assertEquals(flashSecond, attempt.getCurrentQuestion());
        Assertions.assertEquals(flash, attempt.prevQuestion());

        Assertions.assertEquals(flashSecond, attempt.nextQuestion());
        Assertions.assertEquals(flashSecond, attempt.getCurrentQuestion());
        Assertions.assertEquals(flash, attempt.nextQuestion());

    }

    @Test
    void quizPointsTest(){
        Assertions.assertEquals(0, attempt.getPoints());
        attempt.addPoint();
        Assertions.assertEquals(1, attempt.getPoints());
    }

    @Test
    void quizAttemptIndexTest(){
        IQuizable<String> flash = new Flashcard("QuestionOne", "AnswerOne");
        IQuizable<String> flashSecond = new Flashcard("QuestionTwo", "AnswerTwo");

        Assertions.assertEquals(0, attempt.getCurrentQuestionIndex());
        Assertions.assertTrue(attempt.isQuizFinished());

        quiz.addQuestion(flash);
        quiz.addQuestion(flashSecond);

        Assertions.assertEquals(0, attempt.getCurrentQuestionIndex());
        Assertions.assertFalse(attempt.isQuizFinished());

        attempt.nextQuestion();
        Assertions.assertEquals(1, attempt.getCurrentQuestionIndex());
        Assertions.assertTrue(attempt.isQuizFinished());
    }


    @Test
    void quizAttemptGetQuizTest(){
        Assertions.assertEquals(quiz, attempt.getQuiz());
    }

    @Test
    void quizAltCreation(){
        IQuizable<String> flash = new Flashcard("QuestionOne", "AnswerOne");
        IQuizable<String> flashSecond = new Flashcard("QuestionTwo", "AnswerTwo");
        List<IQuizable<?>> questions = new ArrayList<>(){
            {
                add(flash);
                add(flashSecond);
            }
        };
        List<Subject> tags = new ArrayList<>(){
            {
                add(Subject.Biology);
                add(Subject.Biochemistry);
            }
        };

        Quiz quiz1 = new Quiz("test name", questions, tags, "UIEFBJHWQNKICQW67271", "user1");
        Assertions.assertEquals("test name", quiz1.getName());
        quiz1.setName("this is a test name");
        Assertions.assertEquals("this is a test name", quiz1.getName());
        Assertions.assertEquals("user1", quiz1.getCreatedBy());
        Assertions.assertEquals("UIEFBJHWQNKICQW67271", quiz1.getId());

        assertTrue(quiz1.getTags().contains(Subject.Biology));
        quiz1.tagSelected(Subject.Biology);
        assertFalse(quiz1.getTags().contains(Subject.Biology));
        quiz1.tagSelected(Subject.Biology);
        assertTrue(quiz1.getTags().contains(Subject.Biology));
    }
}