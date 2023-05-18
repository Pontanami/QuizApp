package com.example.quizapp.quiz;
import com.example.quizapp.NavigationStack;
import com.example.quizapp.firebase.FirebaseQuizRepository;
import com.example.quizapp.firebase.FirebaseTakenQuizRepository;
import com.example.quizapp.firebase.FirebaseUserRepository;
import com.example.quizapp.interfaces.IObservable;
import com.example.quizapp.interfaces.IObserver;
import com.example.quizapp.quiz.takeQuiz.TakeQuizController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuizThumbnail extends AnchorPane implements IObservable {

    private Quiz quiz;

    @FXML
    private ImageView quizThumbnail;
    @FXML
    private ImageView xImage;

    @FXML
    private Text quizName;

    private final NavigationStack navigation = NavigationStack.getInstance();
    private final FirebaseQuizRepository quizRepository = new FirebaseQuizRepository();
    private final FirebaseTakenQuizRepository takenQuizRepo = new FirebaseTakenQuizRepository();
    private final List<IObserver> observers = new ArrayList<>();
    private final Timeline animationBack;
    private final Timeline animation;
    private FlowPane quizPane;

    private static final double DEFAULT = 0.0;
    private static final double HOVERED = 1.2;
    private static final int DURATION_MS = 200;

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
        xImage.setImage(new Image(String.valueOf(getClass().getResource("/img/delete.png"))));

        quizName.setText(quiz.getName());
        xImage.setDisable(true);
        xImage.setVisible(false);
        xImage.setPreserveRatio(true);

        KeyValue scaleXKeyFrame = new KeyValue(xImage.scaleXProperty(), HOVERED);
        KeyValue scaleYKeyFrame = new KeyValue(xImage.scaleYProperty(), HOVERED);
        KeyValue scaleXBackKeyFrame = new KeyValue(xImage.scaleXProperty(), DEFAULT);
        KeyValue scaleYBackKeyFrame = new KeyValue(xImage.scaleYProperty(), DEFAULT);
        KeyFrame hoverKeyFrame = new KeyFrame(Duration.millis(DURATION_MS), scaleXKeyFrame, scaleYKeyFrame);
        KeyFrame unHoverKeyFrame = new KeyFrame(Duration.millis(DURATION_MS), scaleXBackKeyFrame, scaleYBackKeyFrame);

        animation = new Timeline();
        animation.getKeyFrames().add(hoverKeyFrame);
        animationBack = new Timeline();
        animationBack.getKeyFrames().add(unHoverKeyFrame);

    }
    public QuizThumbnail(Quiz quiz, FlowPane myQuizFlow) {
        this(quiz);
        this.quizPane = myQuizFlow;
    }

    /**
     * navigate to the clicked quiz
     */
    @FXML
    public void navigateToQuiz() {
        navigation.pushView(new TakeQuizController(quiz, 0, quiz.getQuestions().size()));
    }

    /**
     * removes the quiz where the red cross was clicked
     */
    @FXML
    public void removeQuiz(MouseEvent event){
        takenQuizRepo.removeTakenQuiz(quiz.getId(), FirebaseUserRepository.getAuth().getCurrentUser().getId());
        quizRepository.removeQuiz(quiz.getId());
        notifySubscribers();
        event.consume();
    }

    /**
     * shows the red cross that enables the removal of a quiz when hovered
     */
    @FXML
    public void showX(){
        if(quizPane != null) {
            if (quizPane.getChildren().contains(this)){
                xImage.setDisable(false);
                xImage.setVisible(true);
                Duration rest = animationBack.getDelay();
                animationBack.stop();
                animation.playFrom(rest);
            }
        }
    }

    /**
     * hides the red cross when the mouse is no longer hovering a quiz
     */
    @FXML
    public void hideX(){
        Duration rest = animation.getDelay();
        animation.stop();
        animationBack.playFrom(rest);
    }

    @Override
    public void subscribe(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifySubscribers() {
        for (IObserver observer: observers){
            observer.update();
        }
    }
}
