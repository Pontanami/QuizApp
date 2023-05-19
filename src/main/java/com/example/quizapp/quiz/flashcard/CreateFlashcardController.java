package com.example.quizapp.quiz.flashcard;

import com.example.quizapp.hints.*;
import com.example.quizapp.quiz.*;
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

    private FlashCardQuizController questionManager;
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
    public CreateFlashcardController(FlashCardQuizController questionManager) {
        setup(questionManager);
        hintDropdown.getSelectionModel().selectFirst();
    }

    public CreateFlashcardController(FlashCardQuizController questionManager, Flashcard question) {
        setup(questionManager);
        if (question.getWordHint() != null) {
            hintDropdown.getSelectionModel().select(question.getWordHint().getClass().getSimpleName());
            if (question.getWordHint().getClass().getSimpleName().equals("TextHint"))
                hintField.setText(question.showHint());
        } else {
            hintDropdown.getSelectionModel().selectFirst();
        }
        frontSide.setText(question.getQuestion());
        backSide.setText(question.getAnswer());
    }

    private void setup(FlashCardQuizController questionManager) {
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
                "No Hint",
                HalfWordHint.class.getSimpleName(),
                OneLetterHint.class.getSimpleName(),
                TextHint.class.getSimpleName())
        );
        hintDropdown.getSelectionModel().selectFirst();

        hintDropdown.valueProperty().addListener((obs, oldVal, newVal) -> {
            chosenHint = (String) newVal;

            if(chosenHint.equals(TextHint.class.getSimpleName())){
                hintField.setVisible(true);
                inputValidator.setFieldValid(hintField, false);
            } else {
                hintField.setVisible(false);
                hintField.setText(null);
                inputValidator.setFieldValid(hintField, true);
            }
        });

        textFields.add(inputValidator.createValidationTextField(frontSide));
        textFields.add(inputValidator.createValidationTextField(backSide));
        textFields.add(inputValidator.createValidationTextFieldValidStart(hintField));
    }

    /**
     * This method creates the {@link IQuizable} object
     * @return the created flashcard
     */
    public IQuizable<String> createQuestion(){
        IHint<String> hint = getHint(chosenHint);
        return new Flashcard(frontSide.getText(), backSide.getText(), hint);
    }

    public boolean isAbleToCreate() {
        for(var field : textFields){
            if(!inputValidator.isValidTextField(field)){
                return false;
            }
        }

        return true;
    }

    /**
     * This method fetches the specified Hint object
     * @param hint key for getting the hint object
     * @return hint object
     */
    private IHint<String> getHint(String hint){
        Map<String, IHint<String>> hints = new HashMap<>();
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
