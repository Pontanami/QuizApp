module com.example.quizapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.cloud.firestore;
    requires firebase.admin;
    requires com.google.api.apicommon;
    requires com.google.auth.oauth2;
    requires com.google.auth;
    requires google.cloud.core;

    opens com.example.quizapp to javafx.fxml;
    exports com.example.quizapp;
    exports com.example.quizapp.user;
    opens com.example.quizapp.user to javafx.fxml;
}