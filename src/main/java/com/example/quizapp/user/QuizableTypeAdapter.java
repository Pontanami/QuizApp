package com.example.quizapp.user;

import com.example.quizapp.interfaces.IQuizable;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * This class is used to serialize and deserialize {@link IQuizable} objects from and to JSON using the gson library.
 */
public class QuizableTypeAdapter implements JsonSerializer<IQuizable<?>>, JsonDeserializer<IQuizable<?>> {
    /**
     * This method serializes a {@link IQuizable} object to JSON.
     * @param src the {@link IQuizable} object to be serialized
     * @param typeOfSrc the type of the {@link IQuizable} object
     * @param context the context
     * @return the serialized {@link IQuizable} object
     */
    @Override
    public JsonElement serialize(IQuizable<?> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("class", src.getClass().getName());
        jsonObject.add("data", context.serialize(src));
        return jsonObject;
    }

    /**
     * This method deserializes a {@link IQuizable} object from JSON.
     * @param json the JSON object
     * @param typeOfT the type of the {@link IQuizable} object
     * @param context the context
     * @return the deserialized {@link IQuizable} object
     * @throws JsonParseException if the JSON is not valid
     */
    @Override
    public IQuizable<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String className = jsonObject.get("class").getAsString();
        JsonElement jsonElement = jsonObject.get("data");
        try {
            Class<?> clazz = Class.forName(className);
            return context.deserialize(jsonElement, clazz);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }
}