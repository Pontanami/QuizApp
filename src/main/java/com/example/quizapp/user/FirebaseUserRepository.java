package com.example.quizapp.user;

import com.example.quizapp.UserQuery;
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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;

/**
 * Singleton class to handle user data in the Firestore database
 * @author Felix, Gustav, Pontus
 */
public class FirebaseUserRepository implements IUserRepository {
    /**
     * Instance of the FirebaseUserRepository singleton
     */
    private static FirebaseUserRepository instance = null;
    /**
     * Reference to the current {@link User} object that is signed in
     */
    private User currentUser;
    /**
     * Reference to the collection of users in the Firestore database
     */
    private final CollectionReference colRef;

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
        Firestore db = FirestoreClient.getFirestore();
        colRef = db.collection("users");
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
    public void createUser(String name, String email, String password) {
        CompletableFuture<User> future = createUserTask(name, email, password);
        future.thenApply(user -> {
            System.out.println("User " + user.getName() + " created");
            return user;
        }).join();
    }

    private CompletableFuture<User> createUserTask(String name, String email, String password) {
        CompletableFuture<User> future = new CompletableFuture<>();
        String hashed_password = generateHash(password);
        try {
            String docID = colRef.document().getId();
            Map<String, Object> data = new HashMap<>();
            data.put("id", docID);
            data.put("name", name);
            data.put("email", email);
            data.put("password", hashed_password);

            ApiFuture<WriteResult> result = colRef.document(docID).set(data);
            ApiFutures.addCallback(result, new ApiFutureCallback<>() {
                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("Failed to reach Firebase");
                    future.completeExceptionally(throwable);
                }
                @Override
                public void onSuccess(WriteResult writeResult) {
                    System.out.println("Reached Firebase");
                    currentUser = new User(docID, name, email, password);
                    future.complete(currentUser);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return future;
    }

    /** Method to log in a user stored in Firestore: Checks if user exists and if password is correct
     * @param name the username of the user
     * @param password the password of the user
     */
    @Override
    public void loginUser(String name, String password) {

        Query q = colRef.whereEqualTo("name", name);
        String hashed_password = generateHash(password);
        ApiFuture<QuerySnapshot> query = q.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            if (documents.isEmpty())
                System.out.println("No matching user");
            else{
                for (DocumentSnapshot document : documents) {
                    if (Objects.equals(document.get("password"), hashed_password)) {
                        currentUser = createObject(document);

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

    /** Method to get one or several users from the Firestore database based on a query

    */
    private Query createUserQuery(UserQuery query) throws IllegalAccessException {
        Query q = colRef;
        for (String key :query.getNonNullFields().keySet())
            q = q.whereEqualTo(key,query.getNonNullFields().get(key));
        return q;
    }

    /** Method to get a user from the Firestore database
     * @param query
     * @return A {@link List} of {@link User} objects with the user's information
     */
    @Override
    public List<User> getUsers(UserQuery query) {
        List<User> user = new ArrayList<>();
        try {
            user = UserQuery(createUserQuery(query));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return user;
    }
    /** Method to get all users from the Firestore database
     * @return A {@link List} of {@link User} objects with the user's information
     */
    public List<User>getUsers(){
        return UserQuery(colRef);
    }
    private List<User> UserQuery(Query q) {
        ApiFuture<QuerySnapshot> query = q.get();
        List<QueryDocumentSnapshot> documents = null;
        try {
            QuerySnapshot querySnapshot = query.get();
            documents = querySnapshot.getDocuments();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        List<User> usersList = new ArrayList<>();
        if (documents.size() > 0) {
            for (QueryDocumentSnapshot document : documents) {
                usersList.add(createObject(document));
            }
        }
        return usersList;
    }
    /**
     * Method to remove a user from the Firestore database
     * @param id the id of the user
     */
    @Override
    public void removeUser(String id) {
        CompletableFuture.runAsync(() -> {
            try {
                colRef.document(id).delete();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }).thenAccept(a -> System.out.println("User removed"));
    }
    /*
    remove user with help of callbacks, might be something we want to use
    public void removeUser(String id, OnSuccess callback){
        try {
            ApiFuture<WriteResult> result = db.collection("users").document(id).delete();
            ApiFutures.addCallback(result, new ApiFutureCallback<WriteResult>() {
                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("fail");
                }

                @Override
                public void onSuccess(WriteResult writeResult) {
                    callback.OnSucess();
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    */

    //Kolla mer på ID och alla doc.get()
    private User createObject(DocumentSnapshot document){
        return new User(document.getId(), (String) document.get("name"),
                        (String) document.get("email"), (String) document.get("password")
        );
    }

    //Väldigt simpel och inte säker lösning, men vi undviker att lagra lösenord i klartext
    /** Method to generate a hash of a password
     * @param password the password to be hashed
     * @return a {@link String} with the hashed password
     */
    private String generateHash(String password){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            generatedPassword = Base64.getEncoder().encodeToString(md.digest(password.getBytes(StandardCharsets.UTF_8)));
        }catch (Exception e){
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
