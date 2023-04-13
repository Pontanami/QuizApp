package com.example.quizapp.multiChoice;

import com.example.quizapp.interfaces.IDisplayable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * An instance of this class represents one single multi choice question
 */
public class MultiChoiceMain implements IDisplayable {
    private MultiChoiceModel model;
    private final String question;

    public MultiChoiceMain(String question){
        this.question = question;
        getChoices();
        getCorrectAnswer();
    }

    //class Question
    //private string question;
    //private Answer answer;
    //public Question(){ answer = new multiChoiceMain(this.question)}

    /**
     * Asks the user for four answers to the question
     */
    private void getChoices(){
        String[] answers = new String[4];
        for (int i = 0; i < 4; i++){
            while (true){

                Scanner choice = new Scanner(System.in);  // Create a Scanner object
                System.out.print("Choice number " + (i+1) + ": ");
                answers[i] = choice.nextLine();  // Read user input

                if (!answers[i].isEmpty()){
                    break;
                }
            }
        }
        model = new MultiChoiceModel(question, answers[0], answers[1], answers[2], answers[3]);
    }

    private void getCorrectAnswer(){
        while (true){
            Scanner correctAns = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Specify the correct answer: ");
            String correct = correctAns.nextLine();  // Read user input

            try {
                model.setCorrectAnswer(correct);
                break;

            }catch (InputMismatchException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Displaying the question and the multiple choices, asking which one is the correct answer to the question.
     * If the user gives the correct answer, add a point for the user, otherwise show the correct answer
     */
    @Override
    public void displayTest(){
        System.out.println(model.getQuestion() + "\n");
        ArrayList<String> choices = model.getChoices();

        for (int i = 0; i < choices.size(); i++){
            System.out.println("Choice " + i + ": " + choices.get(i));
        }

        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("Which one is the correct answer: ");
        String correct = myObj.nextLine();  // Read user input

        if (correct.equals(model.getCorrectAnswer())){
            model.addPoint();
            System.out.println("That's correct");
        } else { //else{ show a hint }
            System.out.println("Sorry, the correct answer is: " + model.getCorrectAnswer());
        }
    }


}
