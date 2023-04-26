package com.example.quizapp.model;

import com.example.quizapp.interfaces.IAnswerable;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class FlashCardController implements IAnswerable {
    @FXML private AnchorPane clickablePane;
    @FXML private Label txtLabel;
    @FXML private Label hintLabel;

    private int textIndex = 0;
    private final String[] termDef = new String[]{"no question to show", "no answer to show"};
    private Flashcard card;

    public void initializeData(Flashcard card){
        termDef[0] = card.getQuestion();
        termDef[1] = card.getAnswer();
        this.card = card;
        hintLabel.setVisible(false);
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

    @Override
    public void showHint() {
        hintLabel.setText(card.showHint());
        hintLabel.setVisible(true);
    }

    @Override
    public void revealAnswer() {
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
}
