package com.example.quizapp.firebase;

import com.example.quizapp.quiz.takeQuiz.TakenQuery;
import com.example.quizapp.quiz.takeQuiz.TakenQuiz;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Class for handling operations to the takenQuizzes collection in the database
 */
public class FirebaseTakenQuizRepository extends FirebaseBaseRepository<TakenQuiz, TakenQuery> implements ITakenQuizRepository{

    private final CollectionReference colref;

    public FirebaseTakenQuizRepository(){
        colref = getCollection("takenQuizzes");
    }

    /**
     * Method for creating a taken quiz object from a document snapshot from Firestore
     * @param doc the {@link DocumentSnapshot} we want to create the object from
     * @return a {@link TakenQuiz} object
     */
    @Override
    TakenQuiz createObject(DocumentSnapshot doc) {
         return new TakenQuiz(doc.get("quizId").toString(),
                                doc.get("userId").toString(),
                                Math.toIntExact((Long) doc.get("score")),
                               doc.getDate("date"));
    }

    /**
     * Method for creating a Firestore query based on the query object
     * @param query the query object we want to use to create the query
     * @return a query object that can be used to get the data from the database
     */
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
    /**
     * Method for uploading a taken quiz to the database
     * @param quizId the id of the quiz that was taken
     * @param userId the id of the user that took the quiz
     * @param score the score the user got on the quiz
     */
    @Override
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

    /**
     * Method for getting taken quizzes from the database
     * @param query the query object we want to use to get the quizzes
     * @return a list of {@link TakenQuiz} objects
     */
    @Override
    public List<TakenQuiz> getTakenQuizzes(TakenQuery.TakenQueryBuilder query){
        List<TakenQuiz> takenQuizs = new ArrayList<>();
        try {
            takenQuizs = getQueryResult(createQuery(query.build()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return takenQuizs;
    }
}
