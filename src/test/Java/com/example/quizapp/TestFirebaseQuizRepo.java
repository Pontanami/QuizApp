package com.example.quizapp;

import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.quiz.flashcard.Flashcard;
import com.example.quizapp.hints.OneLetterHint;
import com.example.quizapp.quiz.tags.Subject;
import com.example.quizapp.hints.TextHint;
import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.quiz.Quiz;
import com.example.quizapp.quiz.QuizQuery;
import com.example.quizapp.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TestFirebaseQuizRepo {

    private FirebaseQuizRepository repo = new FirebaseQuizRepository();
    private User currentUser;
    private Quiz quiz;
    private Quiz quiz2;
    private QuizQuery.QuizQueryBuilder query;

    Flashcard flashcard1 = new Flashcard("What the capital of Sweden?", "Stockholm", new OneLetterHint("Stockholm"));
    Flashcard flashcard2 = new Flashcard("What is 1+1", "2", new TextHint("ett plus ett är två"));
    @BeforeEach
    void setup(){
        final List<IQuizable<?>> questions = new ArrayList<>();
        questions.add(flashcard1);
        questions.add(flashcard2);
        currentUser = new User("123", "bob", "bob@gmail.com", "bob");
        query = new QuizQuery.QuizQueryBuilder();
        quiz = new Quiz("testQuiz", questions, List.of(Subject.Mathematics, Subject.Economics), "1", currentUser.getId(), 0, 0);
        quiz2 = new Quiz("testQuiz", questions, List.of(Subject.Mathematics), "1", currentUser.getId(), 0, 0);
    }
    @Test
    public void testUploadQuiz() {
        repo.uploadQuiz(quiz, currentUser);
        Quiz currentQuiz = repo.getQuiz(query.setName("testQuiz")).get(0);
        Assertions.assertEquals(quiz.getName(), "testQuiz");
        repo.removeQuiz(currentQuiz.getId());
    }
    @Test
    public void testGetQuizFromUser() {
        repo.uploadQuiz(quiz, currentUser);
        Quiz currentQuiz = repo.getQuiz(query.setCreatedBy("123")).get(0);
        Assertions.assertEquals(quiz.getName(), "testQuiz");
        repo.removeQuiz(currentQuiz.getId());
    }
    @Test
    public void testRemoveQuiz() {
        repo.uploadQuiz(quiz, currentUser);
        Quiz currentQuiz = repo.getQuiz(query.setCreatedBy("123")).get(0);
        repo.removeQuiz(currentQuiz.getId());
        Assertions.assertTrue(repo.getQuiz(query.setCreatedBy("123")).isEmpty());
    }
    @Test
    public void testAddTagsToSearch(){
        repo.uploadQuiz(quiz, currentUser);
        repo.uploadQuiz(quiz2, currentUser);
        List<Quiz> quizzes1 = repo.getQuiz(query.setTags(Set.of(Subject.Economics)).setName("testQuiz"));
        Assertions.assertEquals(1, quizzes1.size());
        query.setTags(Set.of(Subject.Mathematics));
        List<Quiz> quizzes2 = repo.getQuiz(query);
        Assertions.assertEquals(2, quizzes2.size());
        for (Quiz quiz : quizzes2) {
            repo.removeQuiz(quiz.getId());
        }

    }
}
