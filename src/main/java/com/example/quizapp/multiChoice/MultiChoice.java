package com.example.quizapp.multiChoice;

import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.model.IHint;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MultiChoice implements IQuizable<Object> {
    private final List<String> choices;
    private final String question;
    private final String answer;
    private IHint<List<String>> hint;

    public MultiChoice(String question, String answer, List<String> choices, IHint hint){
        this.choices = choices;
        this.answer = answer;
        this.question = question;
        this.hint = hint;
    }

    private void createHint() {
        System.out.println("What type of hint:");
        System.out.println("1 - Text hint");
        System.out.println("2 - Eliminate some choices");
        System.out.println("3 - No hint");
        String chosenOption = new Scanner(System.in).nextLine();
        //String correctAnswer = model.getCorrectAnswer();

        switch(chosenOption){
            case "1":
                System.out.println("Enter your text hint:");
                chosenOption =  new Scanner(System.in).nextLine();
                //model = new MultiChoiceModel(question, model.getChoices().get(0), model.getChoices().get(1), model.getChoices().get(2), model.getChoices().get(3), new TextHint(chosenOption));
                break;

            case "2":
                //EliminateChoiceHint eliminateChoiceHint = new EliminateChoiceHint(model.getChoices(), model.getChoices().get(Integer.parseInt(chosenOption)-1));

                //model = new MultiChoiceModel(question, model.getChoices().get(0), model.getChoices().get(1), model.getChoices().get(2), model.getChoices().get(3), eliminateChoiceHint);

                break;
            default:
                //model = new MultiChoiceModel(question, model.getChoices().get(0), model.getChoices().get(1), model.getChoices().get(2), model.getChoices().get(3), null);
                break;
        }
        //model.setCorrectAnswer(correctAnswer);

    }

    public List<String> getChoices() {
        return choices;
    }

    @Override
    public String getAnswer() {
        return answer;
    }

    @Override
    public List<String> showHint() {
        return hint.showHint();
    }

    @Override
    public String getQuestion() {
        return question;
    }


}
