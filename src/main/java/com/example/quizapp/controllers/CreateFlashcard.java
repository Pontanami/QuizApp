package com.example.quizapp.controllers;

import com.example.quizapp.interfaces.ICreateQuestion;
import com.example.quizapp.interfaces.IQuizManager;
import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.model.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CreateFlashcard extends AnchorPane implements ICreateQuestion<String> {

    private IQuizManager questionManager;
    @FXML private TextField frontSide;
    @FXML private TextField backSide;
    @FXML private ComboBox hintDropdown;
    @FXML private TextField hintField;
    private String chosenHint;

    /**
     * Creates a CreateFlashcard object with a question manager.
     * @param questionManager the responsible components for all CreateFlashcard controllers.
     */
    public CreateFlashcard(IQuizManager questionManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createFlashcard.fxml"));
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
                TextHint.class.getSimpleName())
        );

        hintDropdown.valueProperty().addListener((obs, oldVal, newVal) -> {
            chosenHint = (String) newVal;
        });
    }

    /**
     * This method creates the Quizable object
     * @return the created flashcard
     */
    public IQuizable<String> createQuestion(){
        IHint hint = getHint(chosenHint);
        return new Flashcard(frontSide.getText(), backSide.getText(), hint);
    }

    /**
     * This method fetches the specified Hint object
     * @param hint key for getting the hint object
     * @return hint object
     */
    private IHint getHint(String hint){
        Map<String, IHint> hints = new HashMap<>();
        hints.put(TextHint.class.getSimpleName(), new TextHint(hintField.getText()));
        hints.put(OneLetterHint.class.getSimpleName(), new OneLetterHint(frontSide.getText()));
        hints.put(HalfWordHint.class.getSimpleName(), new HalfWordHint(frontSide.getText()));
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
