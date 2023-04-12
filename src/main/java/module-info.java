module com.example.quizapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires google.cloud.firestore;
    requires firebase.admin;


    opens com.example.quizapp to javafx.fxml;
    exports com.example.quizapp;
}