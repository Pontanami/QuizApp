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
     * Method for accessing the fields from an object with are not null
     * @return a map containing the fields and values
     * @throws IllegalAccessException If this class is not allowed to access the private fields, this exception is thrown
     */
    protected Map<String, Object> getNonNullFields() throws IllegalAccessException {
        loopFields();
        return map;
    }

    /**
     * Let's each subclass define it's own implementation of how to itterate over each field in the class
     * @throws IllegalAccessException If this class is not allowed to access the private fields, this exception is thrown
     */
    protected abstract void loopFields() throws IllegalAccessException;

    /**
     * Adds the name and the value of the field to the map
     * @param name the name of the field
     * @param value the value of the field
     */
    protected void addFields(String name, Object value){
        if (value != null){
            map.put(name, value);
        }
    }
}
