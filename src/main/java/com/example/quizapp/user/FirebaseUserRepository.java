package com.example.quizapp.user;

import com.example.quizapp.UserQuery;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.CompletableFuture;

/**
 * Singleton class to handle user data in the Firestore database
 * @author Felix, Gustav, Pontus
 */
public class FirebaseUserRepository extends FirebaseBaseRepository<User> implements IUserRepository {
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
        String docID = getDocumentID(colRef);
        Map<String, Object> data = new HashMap<>();
        data.put("id", docID);
        data.put("name", name);
        data.put("email", email);
        data.put("password", password);

        CompletableFuture<Void> future = addDataToDb(data, colRef, docID);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        currentUser = new User(docID, name, email, password);
        System.out.println("User created");
    }

    /** Method to log in a user stored in Firestore: Checks if user exists and if password is correct
     * @param name the username of the user
     * @param password the password of the user
     */
    @Override
    public void loginUser(String name, String password) {
        Query q = colRef.whereEqualTo("name", name);
        ApiFuture<QuerySnapshot> query = q.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            if (documents.isEmpty())
                System.out.println("No matching user");
            else{
                for (DocumentSnapshot document : documents) {
                    if (Objects.equals(document.get("password"), password)) {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        return getQueryResult(q);
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
}
