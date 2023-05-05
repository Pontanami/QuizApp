package com.example.quizapp.quiz;

import com.example.quizapp.quiz.tags.Subject;
import com.example.quizapp.user.MyProfileController;
import com.example.quizapp.firebase.FirebaseQuizRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.CheckComboBox;
import javafx.scene.layout.FlowPane;

import java.io.IOException;
import java.net.URL;
import java.util.*;

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

    @FXML
    private CheckComboBox<Subject> tagsCheckComboBox = new CheckComboBox<>();


    /**
     * Represents the "sub view" that includes all {@link QuizThumbnail} objects.
     * @param parentPane The {@link AnchorPane} to populate and navigate from
     */
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

        final ObservableList<Subject> tags = FXCollections.observableArrayList();
        tags.addAll(Arrays.asList(Subject.values()));
        tagsCheckComboBox.getItems().addAll(tags);
    }


    /**
     * Searches for quizzes by name, and updates the collection accordingly
     */
    @FXML
    public void searchQuizzes() {
        String query = inputField.getText();
        ObservableList<Subject> checkedItems = tagsCheckComboBox.getCheckModel().getCheckedItems();
        Set<Subject> checkedItemsSet = new HashSet<>(checkedItems);
        QuizQuery.QuizQueryBuilder quizQuery = new QuizQuery.QuizQueryBuilder();
        quizQuery.setTags(checkedItemsSet);
        quizList = QuizSearch.search(firebaseQuizRepository.getQuiz(quizQuery), query);
        populateQuizResults();
    }

    private List<Quiz> getAllQuizzes() {
        QuizQuery.QuizQueryBuilder emptyQuery = new QuizQuery.QuizQueryBuilder();
        return QuizSearch.search(firebaseQuizRepository.getQuiz(emptyQuery), "");
    }

    private void populateQuizResults(){
        quizFlowpane.getChildren().clear();
        for (Quiz quiz : quizList) {
            QuizThumbnail quizThumbnail = new QuizThumbnail(quiz, parentPane);
            quizFlowpane.getChildren().add(quizThumbnail);
        }
    }

    /**
     * Navigate to the profile page
     */
    @FXML
    public void navigateToProfile() {
        MyProfileController profileController = new MyProfileController(parentPane);
        parentPane.getChildren().clear();
        parentPane.getChildren().add(profileController);
    }

    /**
     * Navigate to creating a new {@link Quiz}
     */
    @FXML
    public void navigateToCreateQuiz() {
        CreateQuizController createQuizController = new CreateQuizController(parentPane);
        parentPane.getChildren().clear();
        parentPane.getChildren().add(createQuizController);
    }


}
