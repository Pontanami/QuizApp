package com.example.quizapp;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * This class is a generic type adapter for objects that uses parameterized interfaces.
 * @param <T> the type of object to serialize/deserialize
 * @author Pontus
 */
public class GenericTypeSerializer<T> implements JsonSerializer<T>, JsonDeserializer<T> {

    private static final String CLASSNAME = "className";
    private static final String DATA = "data";

    /**
     * This method serializes a generic object {@link T} to JSON.
     * @param src the {@link T} object to be serialized
     * @param typeOfSrc the type of the {@link T} object
     * @param context the context
     * @return the serialized {@link T} object
     */
    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, src.getClass().getName());
        jsonObject.add(DATA, context.serialize(src));
        return jsonObject;
    }

    /**
     * This method deserializes a generic object {@link T} from JSON.
     * @param json the JSON object
     * @param typeOfT the type of the {@link T} object
     * @param context the context
     * @return the deserialized {@link T} object
     * @throws JsonParseException if the JSON is not valid
     */
    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class<?> retrievedClass;
        T result;
        try {
            retrievedClass = Class.forName(className);
            result = context.deserialize(jsonObject.get(DATA), retrievedClass);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
        return result;
    }
}
