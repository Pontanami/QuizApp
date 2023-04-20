package com.example.quizapp.interfaces;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public interface IQuizable<T>{
    String getQuestion();
    T getAnswer();
}
