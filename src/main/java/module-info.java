module com.example.quizapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quizapp to javafx.fxml;
    exports com.example.quizapp;
}