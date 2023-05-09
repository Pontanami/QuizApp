package com.example.quizapp.quiz.flashcard;

import com.example.quizapp.quiz.takeQuiz.IAnswerable;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Optional;

/**
 * Represents the controller of FlashCard.fxml
 * Method initializeData(Flashcard ques) must be called with an instance of Flashcard
 * @see FlashCardController#initializeData(Flashcard)
 */
public class FlashCardController implements IAnswerable {
    @FXML private AnchorPane clickablePane;
    @FXML private Label txtLabel;

    private int textIndex = 0;
    private final String[] termDef = new String[]{"no question to show", "no answer to show"};
    private Flashcard card;

    /**
     * Initialize the values for the current object with the specified FlashCard instance
     * @param card the FlashCard object associated with this FlashCardController instance
     */
    public void initializeData(Flashcard card){
        termDef[0] = card.getQuestion();
        termDef[1] = card.getAnswer();
        this.card = card;
        setText();
    }

    private void setText(){
        txtLabel.setText(termDef[textIndex]);
    }

    private RotateTransition createRotator(Node card) {
        RotateTransition rotator = new RotateTransition(Duration.millis(1000), card);
        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(180);
        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(1);

        card.setScaleX(-1);

        return rotator;
    }

    /**
     * Shows the hint for the current instance of Flashcard
     */
    @Override
    public void showHint() {
        Alert a;
        if (card.getWordHint() != null) {
            String hintName = card.getWordHint().getClass().getSimpleName();
            a = new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Hint type: " + hintName);
        } else {
            a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Hint type: " + "MISSING");
        }
        a.setTitle("Hint");
        a.setContentText(card.showHint());

        Parent parentPane = clickablePane.getParent();
        parentPane.setOpacity(0.2);
        a.showAndWait();
        parentPane.setOpacity(1);
    }

    /**
     * Rotates the AnchorPane that holds the question and answer
     */
    public void rotate(){
        RotateTransition rotator = createRotator(clickablePane);
        rotator.play();
        txtLabel.setText("");
        textIndex = Math.abs((textIndex+1) % 2);
        rotator.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                setText();
            }
        });
    }

    /**
     * Reveals the answer for the current instance of Flashcard
     * @return true if answered correctly false otherwise
     */
    @Override
    public boolean revealAnswer(){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "", ButtonType.NO, ButtonType.YES);
        a.setHeaderText("The answer is " + card.getAnswer() + ". Were you right?");
        a.setTitle("Answer");

        Parent parentPane = clickablePane.getParent();
        parentPane.setOpacity(0.2);
        Optional<ButtonType> result = a.showAndWait();
        parentPane.setOpacity(1);

        return result.get() == ButtonType.YES;
    }
}
