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

    /** Method to create a user and store it in the Firestore database
     * @param name the username of the user
     * @param email the email of the user
     * @param password the password of the user
     */
    @Override
    public void createUser(String name, String email, String password){
        CollectionReference docRef = db.collection("users");
        User user = new User(name, email, password);
        try{
            docRef.add(user);
            System.out.println("User " + name + " created");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /** Method to log in a user stored in Firestore: Checks if user exists and if password is correct
     * @param name the username of the user
     * @param password the password of the user
     */
    public void loginUser(String name, String password) {
        Query q = db.collection("users").whereEqualTo("name", name);
        ApiFuture<QuerySnapshot> future = q.get();
        try {
            QuerySnapshot document = future.get();
            if(document != null) {
                if(Objects.equals(document.getDocuments().get(0).get("password"), password)) {
                    // log in user
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
    /** Method to get a user from the Firestore database
     * @param name the username of the user
     * @return A {@link User} object with the user's information
     */
    public User getUser(String name) {
        CollectionReference users = db.collection("users");
        Query query = users.whereEqualTo("name", name);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();
        try {
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
                return document.toObject(User.class);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        

        return null;
    }
    /** Method to get all users from the Firestore database
     * @return A {@link List} of {@link User} objects with the user's information
     */
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


    public void removeUser(String name) throws ExecutionException, InterruptedException {
        ApiFuture<WriteResult> writeResult = db.collection("users").document(name).delete();
    }
}
