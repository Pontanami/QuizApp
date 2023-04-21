package com.example.quizapp.multiChoice;

import com.example.quizapp.interfaces.IQuizable;

import java.util.Arrays;
import java.util.InputMismatchException;

public class MultiChoice implements IQuizable<String[]> {
    private final String[] choices;
    private final String question;

    public MultiChoice(String question, String[] answers){
        choices = answers;

        if (!Arrays.asList("1", "2", "3", "4").contains(choices[4])){
            throw new InputMismatchException("The alternative " + choices[4] + " is not one of the four choices");
        }

        this.question = question;
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
    @Override
    public String[] getAnswer() {
        return choices;
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

    @Override
    public String getQuestion() {
        return question;
    }


}
