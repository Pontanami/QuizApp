package com.example.quizapp.quiz.flashcard;

import com.example.quizapp.hints.HalfWordHint;
import com.example.quizapp.hints.IHint;
import com.example.quizapp.hints.OneLetterHint;
import com.example.quizapp.hints.TextHint;
import com.example.quizapp.quiz.InputValidator;
import com.example.quizapp.quiz.ICreateQuestion;
import com.example.quizapp.quiz.IQuizManager;
import com.example.quizapp.quiz.IQuizable;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


import java.io.IOException;
import java.util.*;

public class CreateFlashcardController extends AnchorPane implements ICreateQuestion<String> {

    private IQuizManager questionManager;
    @FXML private TextField frontSide;
    @FXML private TextField backSide;
    @FXML private ComboBox hintDropdown;
    @FXML private TextField hintField;
    private String chosenHint;
    private List<TextField> textFields = new ArrayList<>();
    private @FXML Text requiredError;

    private InputValidator inputValidator;

    /**
     * Creates a CreateFlashcard object with a question manager.
     * @param questionManager the responsible components for all CreateFlashcard controllers.
     */
    public CreateFlashcardController(IQuizManager questionManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createFlashcard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.questionManager = questionManager;
        this.inputValidator = new InputValidator(requiredError);

        hintDropdown.setItems(FXCollections.observableArrayList(
                HalfWordHint.class.getSimpleName(),
                OneLetterHint.class.getSimpleName(),
                TextHint.class.getSimpleName(),
                "No Hint")
        );

        hintDropdown.valueProperty().addListener((obs, oldVal, newVal) -> {
            chosenHint = (String) newVal;

            if(chosenHint.equals(TextHint.class.getSimpleName())){
                hintField.setVisible(true);
            } else {
                hintField.setVisible(false);
                hintField.setText(null);
            }
        });

        textFields.add(inputValidator.createValidationTextField(frontSide));
        textFields.add(inputValidator.createValidationTextField(backSide));
        textFields.add(inputValidator.createValidationTextField(hintField));
    }

    /**
     * This method creates the {@link IQuizable} object
     * @return the created flashcard
     */
    public IQuizable<String> createQuestion(){
        IHint hint = getHint(chosenHint);
        return new Flashcard(frontSide.getText(), backSide.getText(), hint);
    }

    public boolean isAbleToCreate() {
        boolean ableToCreate = true;

        for(var field : textFields){
            if(!inputValidator.isValidTextField(field)){
                ableToCreate = false;
            }
        }

        return ableToCreate;
    }

    /**
     * This method fetches the specified Hint object
     * @param hint key for getting the hint object
     * @return hint object
     */
    private IHint getHint(String hint){
        Map<String, IHint> hints = new HashMap<>();
        hints.put(TextHint.class.getSimpleName(), new TextHint(hintField.getText()));
        hints.put(OneLetterHint.class.getSimpleName(), new OneLetterHint(backSide.getText()));
        hints.put(HalfWordHint.class.getSimpleName(), new HalfWordHint(backSide.getText()));
        hints.put("No Hint", null);

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
