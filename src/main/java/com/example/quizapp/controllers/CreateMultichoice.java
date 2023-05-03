package com.example.quizapp.controllers;

import com.example.quizapp.interfaces.ICreateQuestion;
import com.example.quizapp.interfaces.IQuizManager;
import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.model.*;
import com.example.quizapp.multiChoice.EliminateChoiceHint;
import com.example.quizapp.multiChoice.MultiChoice;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.*;

public class CreateMultichoice extends AnchorPane implements ICreateQuestion<String> {

    private IQuizManager questionManager;
    @FXML private TextField questionField;
    @FXML private ComboBox hintDropdown;
    @FXML private TextArea hintArea;
    private String chosenHint;

    @FXML private TextField choice1;
    @FXML private TextField choice2;
    @FXML private TextField choice3;
    @FXML private TextField answerField;

    /**
     * Creates a CreateMultiChoice object with a question manager.
     * @param questionManager the responsible components for all CreateMultiChoice controllers.
     */
    public CreateMultichoice(IQuizManager questionManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createMultiChoice.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.questionManager = questionManager;

        hintDropdown.setItems(FXCollections.observableArrayList(
                HalfWordHint.class.getSimpleName(),
                OneLetterHint.class.getSimpleName(),
                TextHint.class.getSimpleName(),
                EliminateChoiceHint.class.getSimpleName())
        );

        hintDropdown.valueProperty().addListener((obs, oldVal, newVal) -> {
            chosenHint = (String) newVal;
        });
    }

    /**
     * This method creates the Quizable object
     * @return the created MultiChoice Question
     */
    public IQuizable<String> createQuestion(){
        String answer = answerField.getText();
        List<String> incorrectChoices = new ArrayList<>();
        incorrectChoices.add(choice1.getText());
        incorrectChoices.add(choice2.getText());
        incorrectChoices.add(choice3.getText());

        IHint hint = getHint(chosenHint, incorrectChoices, answer);

        IQuizable multiChoiceQuestion = new MultiChoice(questionField.getText(), answer, incorrectChoices, hint);
        return multiChoiceQuestion;
    }

    /**
     * This method fetches the specified Hint object
     * @param hint key for getting the hint object
     * @return hint object
     */
    private IHint getHint(String hint, List<String> incorrectChoices, String answer){
        Map<String, IHint> hints = new HashMap<>();
        hints.put(TextHint.class.getSimpleName(), new TextHint(hintArea.getText()));
        hints.put(OneLetterHint.class.getSimpleName(), new OneLetterHint(answerField.getText()));
        hints.put(HalfWordHint.class.getSimpleName(), new HalfWordHint(answerField.getText()));
        hints.put(EliminateChoiceHint.class.getSimpleName(), new EliminateChoiceHint(incorrectChoices, answer));

        return hints.get(hint);
    }

    /**
     * This method removes question from the quiz
     */
    @FXML
    public void removeQuestion(){
        questionManager.removeQuestion(this);
    }
}
