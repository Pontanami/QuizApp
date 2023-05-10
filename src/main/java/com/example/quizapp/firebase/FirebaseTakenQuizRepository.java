package com.example.quizapp.firebase;

import com.example.quizapp.quiz.QuizQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuiz;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FirebaseTakenQuizRepository extends FirebaseBaseRepository<TakenQuiz, TakenQuery>{

    private final CollectionReference colref;

    public FirebaseTakenQuizRepository(){
        colref = getCollection("takenQuizzes");
    }

    public void uploadTakenQuiz(String quizId, String userId, int score) {
        String docID = getDocumentID(colref);
        Map<String, Object> data = new HashMap<>();
        data.put("quizId", quizId);
        data.put("userId", userId);
        data.put("score", score);
        data.put("date", Timestamp.now());
        CompletableFuture<Void> future = addDataToDb(data, colref, docID);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
    }


    @Override
    TakenQuiz createObject(DocumentSnapshot doc) {
         DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
         Timestamp timestamp = (Timestamp) doc.get("date");
         return new TakenQuiz(doc.get("quizId").toString(),
                                doc.get("userId").toString(),
                                Math.toIntExact((Long) doc.get("score")),
                               doc.getDate("date"));
    }

    @Override
    Query createQuery(TakenQuery query) throws IllegalAccessException {
        Query q = colref;
        for (String key :query.getNonNullFields().keySet()) {
            Object value = query.getNonNullFields().get(key);
            if (Objects.equals(key, "limit")) {
                if (!Objects.equals(value, 0))
                    q = q.limit((Integer) value);
            } else if (Objects.equals(key, "orderBy")) {
                q = q.orderBy((String) value,  Query.Direction.DESCENDING);
            } else
                q = q.whereEqualTo(key, value);
        }
        return q;
    }

    public List<TakenQuiz> getTakenQuizzes(TakenQuery.TakenQueryBuilder query){
        List<TakenQuiz> takenQuizs = new ArrayList<>();
        try {
            takenQuizs = getQueryResult(createQuery(query.build()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return takenQuizs;
    }

    public List<TakenQuiz> getTakenQuizzesLimited(int amount){
        List<TakenQuiz> takenQuizs = new ArrayList<>();
        Query q = colref.orderBy("date", Query.Direction.DESCENDING).limit(amount);
        takenQuizs = getQueryResult(q);

        return takenQuizs;
    }
}
