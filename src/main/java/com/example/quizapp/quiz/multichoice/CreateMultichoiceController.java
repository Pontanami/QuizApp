package com.example.quizapp.quiz.multichoice;

import com.example.quizapp.hints.*;
import com.example.quizapp.quiz.ICreateQuestion;
import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.quiz.InputValidator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateMultichoiceController extends AnchorPane implements ICreateQuestion<String> {

    private MultiChoiceQuizController questionManager;
    @FXML private TextField questionField;
    @FXML private ComboBox hintDropdown;
    @FXML private TextArea hintArea;
    private String chosenHint;
    @FXML private TextField choice1;
    @FXML private TextField choice2;
    @FXML private TextField choice3;
    @FXML private TextField answerField;
    private List<TextField> textFields = new ArrayList<>();
    @FXML private Text requiredError;
    private InputValidator validator;

    /**
     * Creates a CreateMultiChoice object with a question manager.
     * @param questionManager the responsible components for all CreateMultiChoice controllers.
     */
    public CreateMultichoiceController(MultiChoiceQuizController questionManager) {
        setup(questionManager);
        hintDropdown.getSelectionModel().selectFirst();
    }

    public CreateMultichoiceController(MultiChoiceQuizController questionManager, MultiChoice question){
        setup(questionManager);
        if (question.getHint() != null) {
            hintDropdown.getSelectionModel().select(question.getHint().getClass().getSimpleName());
        } else {
            hintDropdown.getSelectionModel().selectFirst();
        }
        questionField.setText(question.getQuestion());
        choice1.setText(question.getChoices().get(0));
        choice2.setText(question.getChoices().get(1));
        choice3.setText(question.getChoices().get(2));
        answerField.setText(question.getAnswer());
    }

    private void setup(MultiChoiceQuizController questionManager) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/createMultiChoice.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.validator = new InputValidator(requiredError);
        this.questionManager = questionManager;

        hintDropdown.setItems(FXCollections.observableArrayList(
                "No Hint",
                EliminateChoiceHint.class.getSimpleName())
        );

        hintDropdown.valueProperty().addListener((obs, oldVal, newVal) -> {
            chosenHint = (String) newVal;
        });

        textFields.add(validator.createValidationTextField(choice1));
        textFields.add(validator.createValidationTextField(choice2));
        textFields.add(validator.createValidationTextField(choice3));
        textFields.add(validator.createValidationTextField(questionField));
        textFields.add(validator.createValidationTextField(answerField));
    }

    /**
     * This method creates the Quizable object
     * @return the created MultiChoice Question
     */
    public IQuizable<String> createQuestion(){
        String answer = answerField.getText();
        List<String> choices = new ArrayList<>();
        choices.add(choice1.getText());
        choices.add(choice2.getText());
        choices.add(choice3.getText());
        choices.add(answer);

        IHint hint = getHint(chosenHint,choices, answer);
        IQuizable multiChoiceQuestion = new MultiChoice(questionField.getText(), answer, choices, hint);
        return multiChoiceQuestion;
    }

    public boolean isAbleToCreate() {
        boolean ableToCreate = true;

        for(var field : textFields){
            if(!validator.isValidTextField(field)){
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
    private IHint getHint(String hint, List<String> incorrectChoices, String answer){
        Map<String, IHint> hints = new HashMap<>();
        hints.put(TextHint.class.getSimpleName(), new TextHint(hintArea.getText()));
        hints.put(OneLetterHint.class.getSimpleName(), new OneLetterHint(answerField.getText()));
        hints.put(HalfWordHint.class.getSimpleName(), new HalfWordHint(answerField.getText()));
        hints.put(EliminateChoiceHint.class.getSimpleName(), new EliminateChoiceHint(incorrectChoices, answer));
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
