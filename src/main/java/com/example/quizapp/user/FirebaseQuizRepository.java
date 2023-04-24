package com.example.quizapp.user;

import com.example.quizapp.Quiz;
import com.example.quizapp.interfaces.IQuizable;
import com.example.quizapp.model.IHint;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
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


    public Quiz getQuiz() {
        Quiz quiz = null;
        try {
            DocumentReference s = colref.document("WWuZnbdwnFQe3s8UGq9t");
            quiz = gson.fromJson(s.get().get().getString("quiz"), Quiz.class);
            System.out.println(quiz.getName());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return quiz;
    }

    public void uploadQuiz(Quiz quiz){
        /*
        Map<String, Object> data = new HashMap<>();
        data.put("name", quiz.getName());
        data.put("tags", quiz.getTags());
        List<Map<String, Object>> questions = new ArrayList<>();
        for (IQuizable question :quiz.getQuestions()){
            Map<String, Object> questionMap = new HashMap<>();
            questionMap.put("question_type",question.getClass().getName());
            questionMap.put("question",question.getQuestion());
            questionMap.put("answer",question.getAnswer());
            Object classname = question.getHint().getClass().getName();
            if(classname.equals("com.example.quizapp.model.TextHint"))
                classname = question.showHint();
            questionMap.put("hint", classname);
            questions.add(questionMap);
        }
        data.put("questions", questions);
        String docID = getDocumentID(colref);
        CompletableFuture<Void> future = addDataToDb(data, colref, docID);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }
         */
        String json = gson.toJson(quiz);
        System.out.println(json);
        Map<String, Object> data = new HashMap<>();
        data.put("quiz", json);
        data.put("name", quiz.getName());
        data.put("tags", quiz.getTags());
        String docID = getDocumentID(colref);
        CompletableFuture<Void> future = addDataToDb(data, colref, docID);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

        }

    }
}
