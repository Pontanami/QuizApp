package com.example.quizapp.firebase;

import com.example.quizapp.quiz.takeQuiz.TakenQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuiz;

import java.util.List;

public interface ITakenQuizRepository {

  void uploadTakenQuiz(String quizId, String userId, int score);
  List<TakenQuiz> getTakenQuizzes(TakenQuery.TakenQueryBuilder query);
  void removeTakenQuiz(String quizId, String userId);

}
