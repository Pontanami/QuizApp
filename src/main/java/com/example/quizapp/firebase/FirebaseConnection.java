package com.example.quizapp.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is a singleton used to create a single connection to the Firebase database since only a
 * single instance of FirebaseApp to the same url may be created.
 */
public class FirebaseConnection {

    private static FirebaseConnection instance = null;

    public static FirebaseConnection getInstance(){
        if (instance == null)
            instance = new FirebaseConnection();

        return instance;
    }

    private FirebaseConnection(){
        try {
            InputStream serviceAccount = getClass().getResourceAsStream("/com/example/quizapp/apiKey");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
