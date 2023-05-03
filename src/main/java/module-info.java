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

    exports com.example.quizapp.controllers;
    opens com.example.quizapp.controllers to javafx.fxml;

    opens com.example.quizapp to javafx.fxml, com.google.gson;
    exports com.example.quizapp;
    opens com.example.quizapp.multiChoice to com.google.gson;
    exports com.example.quizapp.user;
    opens com.example.quizapp.user to javafx.fxml;
    exports com.example.quizapp.interfaces;
    opens com.example.quizapp.interfaces to javafx.fxml;
    exports com.example.quizapp.model to google.cloud.firestore, javafx.fxml;

    opens com.example.quizapp.model to com.google.gson, javafx.fxml;
}