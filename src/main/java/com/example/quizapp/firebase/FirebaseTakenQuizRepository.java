package com.example.quizapp.firebase;

import com.example.quizapp.quiz.TakenQuiz;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FirebaseTakenQuizRepository extends FirebaseBaseRepository{

    private final CollectionReference colref;

    public FirebaseTakenQuizRepository(){
        colref = getCollection("takenQuizzes");
    }

    public void uploadTakenQuiz(String quizId, String userId, int score) {
        String docID = getDocumentID(colref);
        LocalDateTime date = LocalDateTime.now();
        Map<String, Object> data = new HashMap<>();
        data.put("quizId", quizId);
        data.put("userId", userId);
        data.put("score", score);
        data.put("date", date);
        CompletableFuture<Void> future = addDataToDb(data, colref, docID);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
    }


    @Override
    Object createObject(DocumentSnapshot doc) {
        return null;
    }

    @Override
    Query createQuery(Object query) throws IllegalAccessException {
        return null;
    }
}
