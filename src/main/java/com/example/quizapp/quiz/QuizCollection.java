package com.example.quizapp.quiz;

import com.example.quizapp.user.MyProfileController;
import com.example.quizapp.firebase.FirebaseQuizRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class QuizCollection extends AnchorPane implements Initializable {
    private FirebaseQuizRepository firebaseQuizRepository = new FirebaseQuizRepository();
    private List<Quiz> quizList;

    @FXML
    private ScrollPane quizScrollpane;
    @FXML
    private FlowPane quizFlowpane;

    @FXML
    private TextField inputField;

    @FXML
    private AnchorPane parentPane;

    public QuizCollection(AnchorPane parentPane) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/QuizCollection.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.parentPane = parentPane;
        quizList = getAllQuizzes();
        populateQuizResults();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quizFlowpane.setHgap(10);
        quizFlowpane.setVgap(10);
    }

    @FXML
    public void searchQuizzes() {
        String query = inputField.getText();
        quizList = QuizSearch.search(firebaseQuizRepository.getQuiz(new QuizQuery.QuizQueryBuilder()), query);
        populateQuizResults();
    }

    private List<Quiz> getAllQuizzes() {
        return QuizSearch.search(firebaseQuizRepository.getQuiz(new QuizQuery.QuizQueryBuilder()), "");
    }

    private void populateQuizResults(){
        quizFlowpane.getChildren().clear();
        for (Quiz quiz : quizList) {
            QuizThumbnail quizThumbnail = new QuizThumbnail(quiz, parentPane);
            quizFlowpane.getChildren().add(quizThumbnail);
        }
    }

    @FXML
    public void navigateToProfile() {
        MyProfileController profileController = new MyProfileController(parentPane);
        parentPane.getChildren().clear();
        parentPane.getChildren().add(profileController);
    }

    @FXML
    public void navigateToCreateQuiz() {
        CreateQuizController createQuizController = new CreateQuizController(parentPane);
        parentPane.getChildren().clear();
        parentPane.getChildren().add(createQuizController);
    }


}