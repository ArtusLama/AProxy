package de.artus.proxy.util.textcomponent;

import com.google.gson.*;
import lombok.NonNull;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TextComponentDeserializer implements JsonDeserializer<TextComponent> {
    @Override
    public TextComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        // Case 1: Plain String | e.g. "Hello!!" -> {"text": "Hello!!"}
        // Case 2: Array        | e.g. ["Hello", {"text": "!!"}] -> {"text": "Hello", "extra": [{"text": "!!"}]}
        // Case 3: Just JSON    | e.g. {"text": "Hello!!"} -> {"text": "Hello!!"}
        if (jsonElement.isJsonArray()) return parseArray(jsonElement, type, jsonDeserializationContext);


        if (jsonElement.isJsonPrimitive() && jsonElement.getAsJsonPrimitive().isString()) {
            return new TextComponent(jsonElement.getAsString());
        } else {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            return new TextComponent(jsonObject.getAsJsonPrimitive("text").getAsString());
        }
    }

    private TextComponent parseArray(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        Iterator<JsonElement> iterator = jsonArray.iterator();

        TextComponent textComponentMain = parseElement(iterator.next(), type, jsonDeserializationContext);
        List<TextComponent> textComponentExtra = new ArrayList<>();

        for (JsonElement element : jsonArray) textComponentExtra.add(parseElement(element, type, jsonDeserializationContext));
        textComponentMain.extra = textComponentExtra.toArray(new TextComponent[0]);
        return textComponentMain;
    }

    @NonNull
    private TextComponent parseElement(JsonElement element, Type type, JsonDeserializationContext jsonDeserializationContext) {
        if (element.isJsonArray()) {
            for (JsonElement arrayElements : element.getAsJsonArray()) {
                return deserialize(arrayElements, type, jsonDeserializationContext);
            }
        }
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            return new TextComponent(element.getAsJsonPrimitive().getAsString());
        }
        return null;
    }
}
