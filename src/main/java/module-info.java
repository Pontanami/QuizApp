module com.example.quizapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.cloud.firestore;
    requires firebase.admin;
    requires com.google.api.apicommon;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires google.cloud.core;
    requires com.google.gson;
    requires org.controlsfx.controls;
    requires java.desktop;

    exports com.example.quizapp.firebase;
    opens com.example.quizapp.firebase to javafx.fxml, google.cloud.firestore, com.google.gson;

    exports com.example.quizapp.hints;
    opens com.example.quizapp.hints to javafx.fxml, google.cloud.firestore, com.google.gson;

    exports com.example.quizapp.interfaces;
    opens com.example.quizapp.interfaces to javafx.fxml;

    exports com.example.quizapp.quiz;
    opens com.example.quizapp.quiz to javafx.fxml, com.google.gson, google.cloud.firestore;

    exports com.example.quizapp.quiz.flashcard;
    opens com.example.quizapp.quiz.flashcard to javafx.fxml, com.google.gson, google.cloud.firestore;

    exports com.example.quizapp.quiz.multichoice;
    opens com.example.quizapp.quiz.multichoice to javafx.fxml, com.google.gson, google.cloud.firestore;

    exports com.example.quizapp.quiz.tags;
    opens com.example.quizapp.quiz.tags to javafx.fxml, com.google.gson, google.cloud.firestore;

    exports com.example.quizapp.quiz.takeQuiz;
    opens com.example.quizapp.quiz.takeQuiz to javafx.fxml, com.google.gson;

    exports com.example.quizapp.user;
    opens com.example.quizapp.user to javafx.fxml;

    exports com.example.quizapp;

    opens com.example.quizapp.mainview to javafx.fxml;
    opens com.example.quizapp to com.google.gson, google.cloud.firestore, javafx.fxml;
}