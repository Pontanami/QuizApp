package com.example.quizapp.firebase;

import com.example.quizapp.quiz.TakenQuiz;
import com.example.quizapp.user.User;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.TemporalAccessor;
import java.util.*;
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
        data.put("date", date.toString());
        CompletableFuture<Void> future = addDataToDb(data, colref, docID);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
    }


    @Override
    Object createObject(DocumentSnapshot doc) {
         DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
         return new TakenQuiz(doc.get("quizId").toString(),
                                doc.get("userId").toString(),
                                Math.toIntExact((Long) doc.get("score")),
                                LocalDateTime.parse((CharSequence) doc.get("date"),formatter));
    }

    @Override
    Query createQuery(Object query) throws IllegalAccessException {
        return null;
    }

    public List<TakenQuiz> getTakenQuizzes(){
        List<TakenQuiz> takenQuizs = new ArrayList<>();
        takenQuizs = getQueryResult(colref);

        return takenQuizs;
    }
}
