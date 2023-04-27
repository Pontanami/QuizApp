package com.example.quizapp.user;

import com.example.quizapp.UserQuery;
import com.google.cloud.firestore.*;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;

/**
 * Singleton class to handle user data in the Firestore database
 * @author Felix, Gustav, Pontus
 */
public class FirebaseUserRepository extends FirebaseBaseRepository<User, UserQuery> implements IUserRepository {
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
     * Private constructor to create the singleton and initialize the collection reference
     */
    private  FirebaseUserRepository(){
        colRef = getCollection("users");
    };

    /**
     * Method to get the instance of the FirebaseUserRepository singleton
     * @return instance of FirebaseUserRepository
     */
    public static FirebaseUserRepository getAuth(){
        if (instance == null)
            instance = new FirebaseUserRepository();

        return instance;
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
        if (getUsers(new UserQuery.UserQueryBuilder().setEmail(email)).size() > 0)
            System.out.println("email is not unique, already exists");
        else {
            String docID = getDocumentID(colRef);
            String hashed_password = generateHash(password);
            Map<String, Object> data = new HashMap<>();
            data.put("id", docID);
            data.put("name", name);
            data.put("email", email);
            data.put("password", hashed_password);
            CompletableFuture<Void> future = addDataToDb(data, colRef, docID);
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();

            }
            currentUser = new User(docID, name, email, password);
            System.out.println("User created");
        }
    }

    /** Method to log in a user stored in Firestore: Checks if user exists and if password is correct
     * @param email the email of the user
     * @param password the password of the user
     */
    @Override
    public void loginUser(String email, String password) {
        Query q = colRef.whereEqualTo("email", email);
        String hashed_password = generateHash(password);
        List<User> user = getQueryResult(q);
        if (user.isEmpty())
            System.out.println("No matching user");
        else{
            if (Objects.equals(user.get(0).getPassword(), hashed_password)) {
                currentUser = user.get(0);
                System.out.println(currentUser.getName() + " logged in");
            }
            else
                System.out.println("Wrong password for user");
        }
    }

    /** Method to get one or several users from the Firestore database based on a query

    */
    Query createQuery(UserQuery query) throws IllegalAccessException {
        Query q = colRef;
        for (String key :query.getNonNullFields().keySet())
            q = q.whereEqualTo(key,query.getNonNullFields().get(key));
        return q;
    }

    /** Method to get a user from the Firestore database
     * @param query the query to use to get the user
     * @return A {@link List} of {@link User} objects with the user's information
     */
    @Override
    public List<User> getUsers(UserQuery.UserQueryBuilder query) {
        List<User> user = new ArrayList<>();
        try {
            user = getQueryResult(createQuery(query.build()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
    /** Method to get all users from the Firestore database
     * @return A {@link List} of {@link User} objects with the user's information
     */
    public List<User>getUsers(){
        return getQueryResult(colRef);
    }
    /**
     * Method to remove a user from the Firestore database
     * @param id the id of the user
     */
    @Override
    public void removeUser(String id){
       CompletableFuture<Void> future = deleteFromDb(colRef, id);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("User removed");

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
    @Override
    protected User createObject(DocumentSnapshot document){
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
