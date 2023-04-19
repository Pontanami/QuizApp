package com.example.quizapp.multiChoice;

import com.example.quizapp.model.IHint;
import com.example.quizapp.model.TextHint;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
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
        createHint();
    }

    private void createHint() {
        System.out.println("What type of hint:");
        System.out.println("1 - Text hint");
        System.out.println("2 - Eliminate some choices");
        System.out.println("3 - No hint");
        String chosenOption =  new Scanner(System.in).nextLine();
        String correctAnswer = model.getCorrectAnswer();

        switch(chosenOption){
            case "1":
                System.out.println("Enter your text hint:");
                chosenOption =  new Scanner(System.in).nextLine();

                model = new MultiChoiceModel(question, model.getChoices().get(0), model.getChoices().get(1), model.getChoices().get(2), model.getChoices().get(3), new TextHint(chosenOption));
                break;

            case "2":
                EliminateChoiceHint eliminateChoiceHint = new EliminateChoiceHint(model.getChoices(), model.getChoices().get(Integer.parseInt(chosenOption)-1));

                model = new MultiChoiceModel(question, model.getChoices().get(0), model.getChoices().get(1), model.getChoices().get(2), model.getChoices().get(3), eliminateChoiceHint);

                break;
            default:
                model = new MultiChoiceModel(question, model.getChoices().get(0), model.getChoices().get(1), model.getChoices().get(2), model.getChoices().get(3), null);
                break;
        }
        model.setCorrectAnswer(correctAnswer);

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
        model = new MultiChoiceModel(question, answers[0], answers[1], answers[2], answers[3], null);




        //model = new MultiChoiceModel(question, answers[0], answers[1], answers[2], answers[3]);
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

                model.setCorrectAnswer(model.getChoices().get(Integer.parseInt(correct) - 1));
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
        //System.out.println("Show hint: + " + choices.size() + 1);
        System.out.println("Show hint ("+ (choices.size() + 1) + ")");


        Scanner myObj = new Scanner(System.in);

        String alternative = myObj.nextLine();

        System.out.println("\n" + "Which one is the correct answer (1-4): ");
//        System.out.println("\n" + "Do you want a hint (5): ");
        // alternative = myObj.nextLine();

        List<String> newChoices = choices;
        if (alternative.equals(String.valueOf(choices.size() + 1))){
            newChoices = model.showHint();

            for (int i = 0; i < newChoices.size(); i++){
                System.out.println("Choice " + (i+1) + ": " + newChoices.get(i));
            }
        }


        alternative = myObj.nextLine();


        if (newChoices.get(Integer.parseInt(alternative)- 1).equals(model.getCorrectAnswer())){
            model.addPoint();
            System.out.println("That's correct");
            System.out.println("Your total points: " + model.getPoints());
        } else { //else{ show a hint }
            System.out.println("Sorry, the correct answer is: " + model.getCorrectAnswer());
        }
    }


}
