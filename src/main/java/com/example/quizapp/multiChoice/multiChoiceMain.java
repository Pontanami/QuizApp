package com.example.quizapp.multiChoice;

import java.util.ArrayList;
import java.util.Scanner;

public class multiChoiceMain {
    private multiChoiceModel model;
    private final String question = "What is Java?";

    public multiChoiceMain(){
        getChoices();
        getCorrectAnswer();
        displayTest();
    }

    private void getChoices() {
        String[] answers = new String[4];
        for (int i = 0; i < 4; i++){
            while (true){

                Scanner myObj = new Scanner(System.in);  // Create a Scanner object
                System.out.print("Choice number " + (i+1) + ": ");
                answers[i] = myObj.nextLine();  // Read user input

                if (!answers[i].isEmpty()){
                    break;
                }
            }
        }
        model = new multiChoiceModel(answers[0], answers[1], answers[2], answers[3]);
    }

    private void getCorrectAnswer(){
        while (true){
            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Which one is correct answer: ");
            String correct = myObj.nextLine();  // Read user input

            try {
                model.setCorrectAnswer(correct);
                break;

            }catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void displayTest(){
        System.out.println(question);
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
