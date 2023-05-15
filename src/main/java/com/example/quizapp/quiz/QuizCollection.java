package com.example.quizapp.quiz;


import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.quiz.tags.Subject;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.CacheHint;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.controlsfx.control.CheckComboBox;


import java.io.IOException;
import java.net.URL;
import java.util.*;

public class QuizCollection extends AnchorPane implements Initializable {
    private FirebaseQuizRepository firebaseQuizRepository = new FirebaseQuizRepository();
    private List<Quiz> quizList;
    @FXML
    private FlowPane quizFlowpane;

    @FXML
    private TextField inputField;

    NavigationStack navigationStack = NavigationStack.getInstance();


    @FXML
    private CheckComboBox<Subject> tagsCheckComboBox = new CheckComboBox<>();


    /**
     * Represents the "sub view" that includes all {@link QuizThumbnail} objects.
     */
    public QuizCollection() {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/QuizCollection.fxml"));
            fxmlLoader.setRoot(this);
            fxmlLoader.setController(this);

            try {
                fxmlLoader.load();
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
            quizList = getAllQuizzes();
            populateQuizResults();
        }


        @Override
        public void initialize (URL url, ResourceBundle resourceBundle){
            quizFlowpane.setHgap(10);
            quizFlowpane.setVgap(10);

            final ObservableList<Subject> tags = FXCollections.observableArrayList();
            tags.addAll(Arrays.asList(Subject.values()));
            tagsCheckComboBox.getItems().addAll(tags);
            inputField.textProperty().addListener((obs, newVal, oldVal) -> searchQuizzes());
            //inputField.setOnAction(obs -> searchQuizzes());
            tagsCheckComboBox.getCheckModel().getCheckedItems().addListener((ListChangeListener<Subject>) change -> searchQuizzes());

        }


        /**
         * Searches for quizzes by name, and updates the collection accordingly
         */
        @FXML
        public void searchQuizzes () {
            String query = inputField.getText();
            ObservableList<Subject> checkedItems = tagsCheckComboBox.getCheckModel().getCheckedItems();
            Set<Subject> checkedItemsSet = new HashSet<>(checkedItems);
            QuizQuery.QuizQueryBuilder quizQuery = new QuizQuery.QuizQueryBuilder();
            quizQuery.setTags(checkedItemsSet);
            quizList = QuizSearch.search(firebaseQuizRepository.getQuiz(quizQuery), query);
            populateQuizResults();
        }

        /**
         * Returns all quizzes from the database
         * @return a list of all quizzes
         */
        private List<Quiz> getAllQuizzes () {
            QuizQuery.QuizQueryBuilder emptyQuery = new QuizQuery.QuizQueryBuilder();
            return QuizSearch.search(firebaseQuizRepository.getQuiz(emptyQuery), "");
        }

        /**
         * Populates the collection with {@link QuizThumbnail} objects
         */
        private void populateQuizResults () {
            quizFlowpane.getChildren().clear();
            for (Quiz quiz : quizList) {
                QuizThumbnail quizThumbnail = new QuizThumbnail(quiz);
                quizThumbnail.setCache(true);
                quizThumbnail.setCacheShape(true);
                quizThumbnail.setCacheHint(CacheHint.SPEED);
                quizFlowpane.getChildren().add(quizThumbnail);
            }
        }
}
