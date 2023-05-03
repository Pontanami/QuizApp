package com.example.quizapp.quiz;

public interface IQuizable<T>{
    String getQuestion();
    T getAnswer();
    T showHint();
}
