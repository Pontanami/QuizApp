package com.example.quizapp.firebase;

import com.example.quizapp.quiz.Quiz;
import com.example.quizapp.quiz.QuizQuery;
import com.example.quizapp.quiz.IQuizable;
import com.example.quizapp.hints.IHint;
import com.example.quizapp.quiz.tags.Subject;
import com.example.quizapp.GenericTypeSerializer;
import com.example.quizapp.user.User;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Query;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Class for handling operations to the quiz collection in the database
 * @author Pontus, Felix
 */
public class FirebaseQuizRepository extends FirebaseBaseRepository<Quiz, QuizQuery> implements IQuizRepository {

    private final CollectionReference colref;

    Gson gson = new GsonBuilder().setPrettyPrinting()
            .registerTypeAdapter(IQuizable.class, new GenericTypeSerializer<IQuizable<?>>())
            .registerTypeAdapter(IHint.class, new GenericTypeSerializer<IHint<?>>())
            .create();

    public FirebaseQuizRepository(){
        colref = getCollection("quizzes");
    }

    /**
     * Method for creating a quiz object from a document snapshot from Firestore
     * @param doc the {@link DocumentSnapshot} we want to create the object from
     * @return a {@link Quiz} object
     */
    @Override
    Quiz createObject(DocumentSnapshot doc) {
        Quiz quiz;
        Type listType = new TypeToken<ArrayList<IQuizable<?>>>(){}.getType();
        String json = doc.getString("quiz");
        List<IQuizable<?>> questionList = gson.fromJson(json, listType);

        List<String> tagsStringList = (List<String>) doc.get("tags");

        List<Subject> tags = new ArrayList<>();

        if (tagsStringList != null) {
            for (String tag : tagsStringList) {
                try {
                    tags.add(Subject.valueOf(tag));
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        }

        quiz = new Quiz((String)doc.get("name"), questionList, tags,
                (String)doc.get("id"), (String)doc.get("createdBy"),
                Math.toIntExact((Long)doc.get("totalPoints")),Math.toIntExact((Long)doc.get("totalAttempts")));
        return quiz;
    }
     /**
      * Method for creating a query based on the query object
      * @param query the query object we want to use to create the query
      * @return a query object that can be used to get the data from the database
      */
    @Override
    Query createQuery(QuizQuery query) throws IllegalAccessException {
        Query q = colref;
        for (String key : query.getNonNullFields().keySet()){
            Object value = query.getNonNullFields().get(key);
            if (Objects.equals(key, "tags")) {
                q = q.whereArrayContainsAny(key, (List) value);
            } else
                q = q.whereEqualTo(key, value);
        }
        return q;
    }

    /**
     * Method for getting a quiz from the database
     * @param query the query we want to use to get the quiz
     * @return a {@link List} of {@link Quiz} objects that match the query
     */
    @Override
    public List<Quiz> getQuiz(QuizQuery.QuizQueryBuilder query) {

        List<Quiz> quiz = new ArrayList<>();
        try {
            quiz = getQueryResult(createQuery(query.build()));

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    /**
     * Method for getting a single quiz from the database
     * @param query the query we want to use to get the quiz
     * @return a {@link Quiz} object that matches the query
     */
    public Quiz getSingleQuiz(QuizQuery.QuizQueryBuilder query){
        Quiz quiz = null;
        try {
            quiz = getSingleQueryResult(createQuery(query.build()));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    /**
     * Method for uploading a quiz to the database
     * @param quiz the {@link Quiz} we want to upload
     * @param currentUser the {@link User} that is uploading the quiz
     */
    public void uploadQuiz(Quiz quiz, User currentUser){
        String json = gson.toJson(quiz.getQuestions(), new TypeToken<List<IQuizable<?>>>() {}.getType());
        Map<String, Object> data = new HashMap<>();
        String docID = getDocumentID(colref);
        data.put("quiz", json);
        data.put("name", quiz.getName());
        data.put("tags", quiz.getTags());
        data.put("id", docID);
        data.put("createdBy", currentUser.getId());
        data.put("totalPoints", 0);
        data.put("totalAttempts", 0);

        CompletableFuture<Void> future = addDataToDb(data, colref, docID);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
    }

    public void updateQuiz(Quiz quiz){
        String json = gson.toJson(quiz.getQuestions(), new TypeToken<List<IQuizable<?>>>() {}.getType());
        Map<String, Object> data = new HashMap<>();
        data.put("quiz", json);
        data.put("name", quiz.getName());
        data.put("tags", quiz.getTags());
        patchDataToDb(data, colref, quiz.getId());
    }

    public void updateQuizPoints(Quiz quiz, int attemptPoints){
        Map<String, Object> data = new HashMap<>();
        data.put("totalPoints", quiz.getTotalPoints() + attemptPoints);
        data.put("totalAttempts", quiz.getTotalAttempts()+1);
        patchDataToDb(data, colref, quiz.getId());
    }
    /**
     * Method for removing a quiz from the database
     * @param id the id of the quiz we want to remove
     */
    public void removeQuiz(String id){
        CompletableFuture<Void> future = deleteFromDb(colref, id);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("Quiz removed");
    }
}
