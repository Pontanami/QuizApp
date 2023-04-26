package com.example.quizapp;

import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.model.Flashcard;
import com.example.quizapp.model.OneLetterHint;
import com.example.quizapp.model.TextHint;
import com.example.quizapp.user.FirebaseQuizRepository;
import com.example.quizapp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TestFirebaseQuizRepo {

    private FirebaseQuizRepository repo = new FirebaseQuizRepository();
    private User currentUser;
    private Quiz quiz;
    private QuizQuery.QuizQueryBuilder query = new QuizQuery.QuizQueryBuilder();

    Flashcard flashcard1 = new Flashcard("What the capital of Sweden?", "Stockholm", new OneLetterHint("Stockholm"));
    Flashcard flashcard2 = new Flashcard("What is 1+1", "2", new TextHint("ett plus ett är två"));
    @BeforeEach
    void setup(){
        final List<IQuizable<?>> questions = new ArrayList<>();
        questions.add(flashcard1);
        questions.add(flashcard2);
        currentUser = new User("123", "bob", "bob@gmail.com", "bob");
        quiz = new Quiz("testQuiz", questions, List.of(Quiz.Subjects.MATH, Quiz.Subjects.SCIENCE), "1", currentUser.getId());

    }




    @Test
    public void testUploadQuiz() {
        repo.uploadQuiz(quiz, currentUser);
        Quiz currentQuiz = repo.getQuiz(query.setName("testQuiz")).get(0);
        Assertions.assertEquals(quiz.getName(), "testQuiz");
        repo.removeQuiz(currentQuiz.getId());
    }
}
