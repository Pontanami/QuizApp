package com.example.quizapp.quiz;
import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.quiz.takeQuiz.TakeQuizController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class QuizThumbnail extends AnchorPane {

    private Quiz quiz;

    @FXML
    private ImageView quizThumbnail;

    @FXML
    private Text quizName;

    @FXML
    private AnchorPane rootPane;

    NavigationStack navigation = NavigationStack.getInstance();

    /**
     * Represents one single thumbnail for one single {@link Quiz} instance.
     * @param quiz The {@link Quiz} to create a thumbnail for.
     */

    public QuizThumbnail(Quiz quiz) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/QuizThumbnail.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.quiz = quiz;
        quizName.setText(quiz.getName());
        if (isMyQuiz()) {
            fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/editButton.fxml"));
            EditButton editButton = new EditButton(quiz);
            fxmlLoader.setController(editButton);
            fxmlLoader.setRoot(editButton);
            try {
                AnchorPane editButton1 = fxmlLoader.load();
                rootPane.getChildren().add(editButton1);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }



    /**
     * navigate to the clicked quiz
     */
    @FXML
    public void navigateToQuiz() {
        navigation.pushView(new TakeQuizController(quiz));
    }

    public boolean isMyQuiz() {
        return quiz.getCreatedBy().equals(FirebaseUserRepository.getAuth().getCurrentUser().getId());
    }

}
