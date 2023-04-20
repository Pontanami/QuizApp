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
import java.util.concurrent.CompletableFuture;

/**
 * Base class for all repositories that handles the connection to the Firestore database
 * @author Alex, Felix, Pontus
 */
public abstract class FirebaseBaseRepository<T> {
    /**
     * Reference to the Firestore database
     */
    private final Firestore db;

    protected FirebaseBaseRepository(){
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

    /**
     * Method to retrieve a {@link CollectionReference} from the Firestore database
     * @param coll the name of the collection
     */
    public CollectionReference getCollection(String coll){
        return db.collection(coll);
    }
    public String getDocumentID(CollectionReference ref){
        return ref.document().getId();
    }
    /**
     * Method to get the result of a query from the Firestore database
     * @param q the {@link Query} to get the result from
     * @return a {@link List} of the objects that were retrieved from the database
     */
    public List<T> getQueryResult(Query q){
        List<T> objects = new ArrayList<>();
        ApiFuture<QuerySnapshot> query = q.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            if (documents.isEmpty())
                throw new RuntimeException("No documents found");
            else {
                for (DocumentSnapshot doc : documents){
                    objects.add(createObject(doc));
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e.getMessage());
        }
        return objects;
    }
    /**
     * Method to delete a document from the Firestore database
     * @param col the {@link CollectionReference} to delete the document from
     * @param id the id of the document
     * @return a {@link CompletableFuture} that will be completed when the document has been deleted
     */
    protected CompletableFuture<Void> deleteFromDb(CollectionReference col, String id){
        CompletableFuture<Void> future = new CompletableFuture<>();
        ApiFuture<WriteResult> result = col.document(id).delete();
        ApiFutures.addCallback(result, new ApiFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Failed to reach Firebase in deleteFromDb");
                future.completeExceptionally(throwable);
            }
            @Override
            public void onSuccess(WriteResult writeResult) {
                System.out.println("Reached Firebase");
                future.complete(null);
            }
        });
        return future;
    }
    /**
     * Method to add data to the Firestore database
     * @param data the data to be added
     * @param col the collection to add the data to
     * @param id the id of the document
     * @return a {@link CompletableFuture} that will be completed when the data has been added
     */
    protected CompletableFuture<Void> addDataToDb(Map<String, Object> data, CollectionReference col, String id){
        CompletableFuture<Void> future = new CompletableFuture<>();
        ApiFuture<WriteResult> result = col.document(id).set(data);
        ApiFutures.addCallback(result, new ApiFutureCallback<>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("Failed to reach Firebase in addDataToDb");
                future.completeExceptionally(throwable);
            }
            @Override
            public void onSuccess(WriteResult writeResult) {
                System.out.println("Reached Firebase");
                future.complete(null);
            }
        });
        return future;
    }

    /**
     * Method to create an object of type T from a {@link DocumentSnapshot}
     * @param doc the {@link DocumentSnapshot} retrieved from Firestore
     * @return an object of type T
     */
    abstract T createObject(DocumentSnapshot doc);
}