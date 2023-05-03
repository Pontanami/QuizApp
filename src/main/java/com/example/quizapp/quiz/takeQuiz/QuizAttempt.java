package com.example.quizapp.quiz.takeQuiz;

import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.quiz.Quiz;

import java.util.List;

public class QuizAttempt {
    private final Quiz quiz;



    private int currentQuestionIndex = 0;
    private int points = 0;

    public QuizAttempt(Quiz quiz) {
        this.quiz = quiz;
    }

    public IQuizable<?> getCurrentQuestion(){
        try {
            return getQuestions().get(currentQuestionIndex);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("there is no questions to get");
        }
    }

    public Quiz getQuiz(){
        return quiz;
    }

    public IQuizable<?> nextQuestion(){
        if (isInvalidQuestionsSize()){
            return getCurrentQuestion();
        }
        currentQuestionIndex = Math.abs((currentQuestionIndex + 1) % getQuestions().size());
        return getCurrentQuestion();
    }

    public IQuizable<?> prevQuestion(){
        if (isInvalidQuestionsSize()){
            return getCurrentQuestion();
        }
        currentQuestionIndex = Math.abs((currentQuestionIndex - 1) % getQuestions().size());
        return getCurrentQuestion();
    }

    private boolean isInvalidQuestionsSize(){
        return getQuestions().size() == 1 || getQuestions().size() == 0;
    }

    private List<IQuizable<?>> getQuestions(){
        return quiz.getQuestions();
    }
    public void addPoint(){
        points++;
    }
    public int getPoints(){
        return points;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public boolean isQuizFinished(){
        return currentQuestionIndex >= quiz.getQuestions().size()-1;
    }
}
