package com.example.quizapp;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class FirebaseUserRepository implements IUserRepository {

    Firestore db;
    private static FirebaseUserRepository instance = null;

    public static FirebaseUserRepository getAuth(){
        if (instance == null)
            instance = new FirebaseUserRepository();

        return instance;
    }

    private FirebaseUserRepository(){
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
    public void createUser(String name, String email, String password){
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
    public void loginUser(String name, String password) {
        DocumentReference docRef = db.collection("users").document(name);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        try {
            DocumentSnapshot document = future.get();
            if(document.exists()) {
                if(Objects.equals(document.getString("password"), password)) {
                    System.out.println("User " + name + " logged in");
                } else {
                    System.out.println("Wrong password");
                }
            } else {
                System.out.println("No such document!");
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String name) {
        CollectionReference users = db.collection("users");
        Query query = users.whereEqualTo("name", name);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                User user = document.toObject(User.class);
                return user;
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<User> getUsers() {
        ApiFuture<QuerySnapshot> future = db.collection("users").get();
        List<QueryDocumentSnapshot> documents = null;
        try {
            documents = future.get().getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<User> users = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            User user = document.toObject(User.class);
            users.add(user);
        }

        return users;
    }
}
