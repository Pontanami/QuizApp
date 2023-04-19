package com.example.quizapp.user;

import java.util.HashMap;
import java.util.Map;

/**
 * Generic class for simplifying the getNonNullFields, used for querying results from firestore
 * @author Felix Erngård
 */
public abstract class FetchQuery {

    Map<String, Object> map = new HashMap<>();

    /**
     *
     * @return a map
     * @throws IllegalAccessException If this class is not allowed to access the private fields, this exception is thrown
     */
    protected Map<String, Object> getNonNullFields() throws IllegalAccessException {
        loopFields();
        return map;
    }
    public abstract void loopFields() throws IllegalAccessException;

    protected void addFields(String name, Object value){
        if (value != null){
            map.put(name, value);
        }
    }
}
