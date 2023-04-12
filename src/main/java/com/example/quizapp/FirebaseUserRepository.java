package com.example.quizapp;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.cloud.firestore.Firestore;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class FirebaseUserRepository implements IUserRepository {

    Firestore db;
    public FirebaseUserRepository(){
        try {
            InputStream serviceAccount = new FileInputStream("src/main/resources/com/example/quizapp/apiKey");
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        db = FirestoreClient.getFirestore();
    }

    @Override
    public void createUser(String name, String email, String password) throws ExecutionException, InterruptedException {
        DocumentReference docRef = db.collection("users").document(name);
        Map<String, Object> data = new HashMap<>();
        User user = new User(name, email, password);
        data.put("name", user.getName());
        data.put("email", user.getEmail());
        data.put("password", user.getPassword());
        try{
            docRef.set(data);
            System.out.println("User " + name + " created");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public void GetUsers() {

    }
}
