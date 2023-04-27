package com.example.quizapp.user;

import com.example.quizapp.model.IHint;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * This class is used to serialize and deserialize {@link IHint} objects from and to JSON using the gson library.
 */
public class HintTypeAdapter implements JsonSerializer<IHint<?>>, JsonDeserializer<IHint<?>> {

    private static final String CLASSNAME = "className";
    private static final String DATA = "data";

    /**
     * This method serializes a {@link IHint} object to JSON.
     * @param src the {@link IHint} object to be serialized
     * @param typeOfSrc the type of the {@link IHint} object
     * @param context the context
     * @return the serialized {@link IHint} object
     */
    @Override
    public JsonElement serialize(IHint<?> src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, src.getClass().getName());
        jsonObject.add(DATA, context.serialize(src));
        return jsonObject;
    }

    /**
     * This method deserializes a {@link IHint} object from JSON.
     * @param json the JSON object
     * @param typeOfT the type of the {@link IHint} object
     * @param context the context
     * @return the deserialized {@link IHint} object
     * @throws JsonParseException if the JSON is not valid
     */
    @Override
    public IHint<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e.getMessage());
        }
        return context.deserialize(jsonObject.get(DATA), clazz);
    }
}
