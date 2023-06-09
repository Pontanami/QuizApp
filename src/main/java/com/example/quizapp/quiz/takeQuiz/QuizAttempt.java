package com.example.quizapp.quiz.takeQuiz;

import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.quiz.Quiz;

import java.util.List;

public class QuizAttempt {
    private final Quiz quiz;



    private int currentQuestionIndex = 0;
    private int points = 0;

    /**
     * Initiates a new quiz attempt for a certain quiz
     * @param quiz The {@link Quiz} that is going to be attempted
     */
    public QuizAttempt(Quiz quiz) {
        this.quiz = quiz;
    }

    /**
     * @return An instance of {@link IQuizable} that represents the current question
     */
    public IQuizable<?> getCurrentQuestion(){
        try {
            return getQuestions().get(currentQuestionIndex);
        }catch (IndexOutOfBoundsException e){
            throw new IndexOutOfBoundsException("there is no questions to get");
        }
    }

    /**
     * @return The {@link Quiz} that is being attempted
     */
    public Quiz getQuiz(){
        return quiz;
    }

    /**
     * @return An instance of {@link IQuizable} that represents the next question if it exists, otherwise current question
     */
    public IQuizable<?> nextQuestion(){
        if (isInvalidQuestionsSize()){
            return getCurrentQuestion();
        }
        currentQuestionIndex = Math.abs((currentQuestionIndex + 1) % getQuestions().size());
        return getCurrentQuestion();
    }

    /**
     * @return An instance of {@link IQuizable} that represents the previous question if it exists, otherwise current question
     */
    public IQuizable<?> prevQuestion(){
        if (isInvalidQuestionsSize()){
            return getCurrentQuestion();
        }
        currentQuestionIndex = Math.abs((currentQuestionIndex - 1) % getQuestions().size());
        return getCurrentQuestion();
    }

    /**
     * Returns true if the amount of questions in the quiz is less than 2
     * @return
     */
    private boolean isInvalidQuestionsSize(){
        return getQuestions().size() < 2;
    }

    /**
     * @return The list of all {@link IQuizable} questions
     */
    private List<IQuizable<?>> getQuestions(){
        return quiz.getQuestions();
    }

    /**
     * Increase the point counter for the current attempt by one
     */
    public void addPoint(){
        points++;
    }

    /**
     * @return The point counter for the current quiz attempt
     */
    public int getPoints(){
        return points;
    }

    /**
     * @return The index of the current question
     */
    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    /**
     * Returns true if the current question is the last question in the quiz
     */
    public boolean isQuizFinished(){
        return currentQuestionIndex == quiz.getQuestions().size()-1;
    }
}
