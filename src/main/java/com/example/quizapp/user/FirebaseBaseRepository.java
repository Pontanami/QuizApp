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

public abstract class FirebaseBaseRepository<T> {
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

    public CollectionReference getCollection(String coll){
        return db.collection(coll);
    }
    public String getDocumentID(CollectionReference ref){
        return ref.document().getId();
    }

    public List<T> getQueryResult(Query q){
        List<T> objects = new ArrayList<>();
        ApiFuture<QuerySnapshot> query = q.get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            if (documents.isEmpty())
                System.out.println("No matching documents");
            else {
                for (DocumentSnapshot doc : documents){
                    objects.add(createObject(doc));
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return objects;
    }

    private CompletableFuture<Void> deleteFromDbTask(CollectionReference col, String id){
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            ApiFuture<WriteResult> result = col.document(id).delete();
            ApiFutures.addCallback(result, new ApiFutureCallback<WriteResult>() {
                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("fail");
                }

                @Override
                public void onSuccess(WriteResult writeResult) {
                    future.complete(null);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return future;
    }

    public void deleteFromDb(CollectionReference col, String id){
        CompletableFuture<Void> future = deleteFromDbTask(col, id);
        future.thenApply(T -> {
            System.out.println("Data removed");
            return T;
        }).join();
    }

    private CompletableFuture<Void> addDataTask(Map<String, Object> data, CollectionReference col, String id){
        CompletableFuture<Void> future = new CompletableFuture<>();
        try {
            ApiFuture<WriteResult> result = col.document(id).set(data);
            ApiFutures.addCallback(result, new ApiFutureCallback<>() {
                @Override
                public void onFailure(Throwable throwable) {
                    System.out.println("Failed to reach Firebase");
                    future.completeExceptionally(throwable);
                }

                @Override
                public void onSuccess(WriteResult writeResult) {
                    System.out.println("Reached Firebase");
                    future.complete(null);
                }
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return future;
    }

    protected void addDataToDb(Map<String, Object> data, CollectionReference col, String id){
        CompletableFuture<Void> future = addDataTask(data, col, id);
        future.thenApply(T -> {
            System.out.println("Data added");
            return T;
        }).join();
    }

    abstract T createObject(DocumentSnapshot doc);

}
