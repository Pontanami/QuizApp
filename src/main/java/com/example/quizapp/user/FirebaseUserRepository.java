package com.example.quizapp.user;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
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

/**
 * Singleton class to handle user data in the Firestore database
 * @author Felix, Gustav, Pontus
 */
public class FirebaseUserRepository implements IUserRepository {
    private final Firestore db;
    private static FirebaseUserRepository instance = null;
    private User currentUser;

    /**
     * Method to get the instance of the FirebaseUserRepository singleton
     * @return instance of FirebaseUserRepository
     */
    public static FirebaseUserRepository getAuth(){
        if (instance == null)
            instance = new FirebaseUserRepository();

        return instance;
    }
    /** Constructor for FirebaseUserRepository
     * Initializes Firestore database
     */
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
    /** Method to get the current user
     * @return {@link User} object of the current user
     */
    public User getCurrentUser() {
        return currentUser;
    }
    /** Method to create a user and store it in the Firestore database
     * @param name the username of the user
     * @param email the email of the user
     * @param password the password of the user
     */
    @Override
    public void createUser(String name, String email, String password){
        CollectionReference ref = db.collection("users");
        User user = new User(name, email, password);
        try{
            Map<String, Object> data = new HashMap<>();
            data.put("name", name);
            data.put("email", email);
            data.put("password", password);
            ApiFuture<DocumentReference> result = ref.add(data);
            ApiFutures.addCallback(result, new ApiFutureCallback<DocumentReference>() {
                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("couldn't add to database");
                }
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    System.out.println("User " + name + " created");
                }
            });
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    /** Method to log in a user stored in Firestore: Checks if user exists and if password is correct
     * @param name the username of the user
     * @param password the password of the user
     */
    @Override
    public void loginUser(String name, String password) {
        Query q = db.collection("users").whereEqualTo("name", name);
        ApiFuture<QuerySnapshot> query = q.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            if (documents.isEmpty())
                System.out.println("No matching user");
            else{
                for (DocumentSnapshot document : documents) {
                    if (Objects.equals(document.get("password"), password)) {
                        currentUser = new User(name, (String) document.get("email"), password);
                        //log in user
                        System.out.println(currentUser.getName() + " logged in");
                    }
                    else
                        System.out.println("Wrong password for user");
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    /*
     Typ så vi måste göra för att använda queryBuilder för att lägga till vad vi nu vill söka efter
     for i in range(amount of attributes)
        if(method.get() != null)
            r = r.whereEqualTo(method.name, method.get())
     */
    /** Method to get a user from the Firestore database
     * @param name the username of the user
     * @return A {@link User} object with the user's information
     */
    @Override
    public User getUser(String name) {
        CollectionReference users = db.collection("users");
        Query q = users.whereEqualTo("name", name);
        ApiFuture<QuerySnapshot> query = q.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            for (DocumentSnapshot document : documents) {
                return new User(name, (String) document.get("email"), (String) document.get("password"));
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    /** Method to get all users from the Firestore database
     * @return A {@link List} of {@link User} objects with the user's information
     */
    @Override
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
            User user = new User((String) document.get("name"),
                    (String) document.get("email"), (String) document.get("password"));
            users.add(user);
        }
        return users;
    }
    /**
     * Method to remove a user from the Firestore database
     * @param name the username of the user
     */
    @Override
    public void removeUser(String name){
        try {
            db.collection("users").document(name).delete();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
