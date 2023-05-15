package com.example.quizapp.multiChoice;

import com.example.quizapp.firebase.FirebaseTakenQuizRepository;
import com.example.quizapp.quiz.tags.Subject;
import com.example.quizapp.quiz.Quiz;
import com.example.quizapp.quiz.QuizQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuiz;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.cloud.Timestamp;

import java.sql.Time;
import java.util.List;
import java.util.Set;

public class TestFirebaseTakenQuizRepo {

    private FirebaseTakenQuizRepository repo = new FirebaseTakenQuizRepository();
    private TakenQuiz takenQuiz;
    private TakenQuiz takenQuiz2;
    private TakenQuiz takenQuiz3;
    private TakenQuery.TakenQueryBuilder query;

    @BeforeEach
    void setup() {
        query = new TakenQuery.TakenQueryBuilder();
    }

    @Test
    public void testUploadTakenQuiz() {
        repo.uploadTakenQuiz("123", "test", 3);
        repo.uploadTakenQuiz("123", "test", 5);
        repo.uploadTakenQuiz("789", "test", 2);
        List<TakenQuiz> quizzes = repo.getTakenQuizzes(query.setuserId("test"));
        Assertions.assertEquals(2, quizzes.size());
        repo.removeTakenQuiz("123","test");
        repo.removeTakenQuiz("789", "test");
    }
}
