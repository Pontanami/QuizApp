package com.example.quizapp.user;

import com.example.quizapp.Quiz;
import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.model.IHint;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class FirebaseQuizRepository extends FirebaseBaseRepository implements IQuizRepository {

    private CollectionReference colref;

    Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(IQuizable.class, new QuizableTypeAdapter())
            .registerTypeAdapter(IHint.class, new HintTypeAdapter()).create();

    public FirebaseQuizRepository(){
        colref = getCollection("quizzes");
    }

    @Override
    Object createObject(DocumentSnapshot doc) {

        /*
        Class<?> c = Class.forName("mypackage.MyClass");
        Constructor<?> cons = c.getConstructor(String.class);
        Object object = cons.newInstance("MyAttributeValue");
         */
        Quiz quiz = gson.fromJson(doc.getString("quiz"), Quiz.class);
        System.out.println(quiz.getName());
        return null;
    }


    public Quiz getQuiz(String id) {
        Quiz quiz = null;
        try {
            DocumentReference s = colref.document(id);
            Type listType = new TypeToken<ArrayList<IQuizable<?>>>(){}.getType();
            DocumentSnapshot doc = s.get().get();
            String json = doc.getString("quiz");
            List<IQuizable<?>> questionList = gson.fromJson(json, listType);
            quiz = new Quiz((String)doc.get("name"), questionList, (List)doc.get("tags"), (String)doc.get("id")
            , (String)doc.get("createdBy"));

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    public void uploadQuiz(Quiz quiz, User currentUser){
        String json = gson.toJson(quiz.getQuestions(), new TypeToken<List<IQuizable<?>>>() {}.getType());
        System.out.println(json);
        Map<String, Object> data = new HashMap<>();
        String docID = getDocumentID(colref);
        data.put("quiz", json);
        data.put("name", quiz.getName());
        data.put("tags", quiz.getTags());
        data.put("id", docID);
        data.put("createdBy", currentUser.getId());

        CompletableFuture<Void> future = addDataToDb(data, colref, docID);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }

    }
}
