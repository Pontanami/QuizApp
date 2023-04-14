package com.example.quizapp.multiChoice;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * An instance of this class represents one single multi choice question
 */
public class MultiChoice {
    private MultiChoiceModel model;
    private final String question;

    public MultiChoice(String question){
        this.question = question;
        createChoices();
        createCorrectAnswer();
    }

    /**
     * Asks the user for four answers to the question
     */
    private void createChoices(){
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

    /**
     * Asks the user to specify which of the four specified choices is the correct answer
     */
    private void createCorrectAnswer(){
        while (true){
            Scanner correctAns = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Specify the correct answer (1-4): ");
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
    public void displayTest(){
        System.out.println(model.getQuestion() + "\n");
        ArrayList<String> choices = model.getChoices();

        for (int i = 0; i < choices.size(); i++){
            System.out.println("Choice " + (i+1) + ": " + choices.get(i));
        }

        Scanner myObj = new Scanner(System.in);
        System.out.println("\n" + "Which one is the correct answer (1-4): ");
        String correct = myObj.nextLine();

        if (correct.equals(model.getCorrectAnswer())){
            model.addPoint();
            System.out.println("That's correct");
            System.out.println("Your total points: " + model.getPoints());
        } else { //else{ show a hint }
            System.out.println("Sorry, the correct answer is: " + model.getCorrectAnswer());
        }
    }


}
