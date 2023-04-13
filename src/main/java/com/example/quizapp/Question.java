package com.example.quizapp;

import com.example.quizapp.interfaces.IDisplayable;
import com.example.quizapp.multiChoice.MultiChoiceMain;

import java.util.Scanner;

/**
 * An instance of the class represents one single question
 */
public class Question {
    private final String question;
    private IDisplayable answer;

    public Question(){
        this.question = askQuestion();
        typeOfQuestion();
        System.out.println("\n");
        answer.displayTest(); //Can be ran when the student takes the test instead
    }

    /**
     * Asks the user for the question he/she wants to create
     * @return the user question
     */
    private String askQuestion(){
        Scanner myQuestion = new Scanner(System.in);
        System.out.println("What is your question: ");
        return myQuestion.nextLine();
    }

    /**
     * Asks the user for the type of the answer which could be either a multi choice or a simple flashcard
     */
    private void typeOfQuestion(){
        Scanner myQuestion = new Scanner(System.in);
        System.out.println("What is the type of your question (m/f): ");
        String type = myQuestion.nextLine();
        if (type.equals("m")){
            answer = new MultiChoiceMain(question);
        }else if (type.equals("f")){
            //call flashcards start
        }else{
            System.out.println("invalid type!");
            typeOfQuestion();
        }
    }

    public IDisplayable getAnswer(){
        return answer;
    }
}
