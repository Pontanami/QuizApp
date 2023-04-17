package com.example.quizapp.user;

import com.example.quizapp.OnSuccess;
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

public class FirebaseBaseRepository {
    private final Firestore db;
    private static FirebaseBaseRepository instance = null;

    public static FirebaseBaseRepository getAuth(){
        if (instance == null)
            instance = new FirebaseBaseRepository();

        return instance;
    }
    private FirebaseBaseRepository(){
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

    public DocumentReference getDocument(CollectionReference ref){
        return ref.document();
    }

    public DocumentReference getDocumentbyId(CollectionReference ref, String id){
        return ref.document(id);
    }

    public String getDocumentID(CollectionReference ref){
        return ref.document().getId();
    }

    public String getDocumentID(DocumentReference doc){
        return doc.getId();
    }

    public ApiFuture<WriteResult> inputData(DocumentReference doc, Map<String, Object> data){
        return doc.set(data);
    }

    public ApiFuture<WriteResult> deleteDocument(String ref, String id){
        return db.collection(ref).document(id).delete();
    }

    public Query runQuery(String ref, String field, Object value){
        return db.collection(ref).whereEqualTo(field, value);
    }

    //Ska denna vara med? För verkar använda snapshot
    public ApiFuture<QuerySnapshot> querySnapshot(){
        return null;
    }
}
