package de.artus.proxy.util.textcomponent;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class TextComponentAdapter extends TypeAdapter<TextComponent> {
    private final Gson gson;

    @Override
    public void write(JsonWriter jsonWriter, TextComponent textComponent) throws IOException {
        gson.toJson(textComponent, TextComponent.class, jsonWriter);
    }

    @Override
    public TextComponent read(JsonReader jsonReader) throws IOException {
        switch (jsonReader.peek()) {
            case BEGIN_ARRAY -> {
                jsonReader.beginArray();
                TextComponent textComponentMain = read(jsonReader);

                List<TextComponent> textComponentExtra = new ArrayList<>();
                while (jsonReader.peek() != JsonToken.END_ARRAY) textComponentExtra.add(read(jsonReader));

                textComponentMain.extra = textComponentExtra.toArray(new TextComponent[0]);
                jsonReader.endArray();
                return textComponentMain;
            }
            case BEGIN_OBJECT -> {
                return gson.getDelegateAdapter(new TextComponentAdapterFactory(), new TypeToken<TextComponent>() {}).read(jsonReader);
            }
            case STRING -> {
                return new TextComponent(jsonReader.nextString());
            }
            default ->
                    throw new JsonParseException("Expected BEGIN_ARRAY, BEGIN_OBJECT, or STRING, but got " + jsonReader.peek());
        }
    }
}