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
import java.util.Objects;

@AllArgsConstructor
public class TextComponentAdapter extends TypeAdapter<TextComponent> {
    private final Gson gson;

    @Override
    public void write(JsonWriter jsonWriter, TextComponent textComponent) throws IOException {
        gson.getDelegateAdapter(new TextComponentAdapterFactory(), new TypeToken<TextComponent>() {}).write(jsonWriter, nullOutSuperfluousFields(textComponent));
    }

    private <T> T check(T field, T defaultValue) {
        return field == null || Objects.equals(field, defaultValue) ? null : field;
    }

    private TextComponent nullOutSuperfluousFields(TextComponent textComponent) {
        return new TextComponent(textComponent.type,
                check(textComponent.extra, List.of()), // VERY IMPORTANT: NO EMPTY LISTS ALLOWED PER DOCUMENTATION
                textComponent.color,
                check(textComponent.bold, false),
                check(textComponent.italic, false),
                check(textComponent.underlined, false),
                check(textComponent.strikethrough, false),
                check(textComponent.obfuscated, false),
                check(textComponent.font, TextComponent.FONT_DEFAULT),
                check(textComponent.insertion, ""),
                textComponent.clickEvent,
                textComponent.hoverEvent,
                check(textComponent.text, ""),
                textComponent.translate,
                check(textComponent.with, List.of()),
                textComponent.keybind,
                textComponent.score,
                textComponent.selector,
                check(textComponent.separator, TextComponent.fromString(", ")),
                textComponent.nbt,
                check(textComponent.interpret, true),
                textComponent.block,
                textComponent.entity,
                textComponent.storage
        );
    }

    @Override
    public TextComponent read(JsonReader jsonReader) throws IOException {
        switch (jsonReader.peek()) {
            case BEGIN_ARRAY -> {
                jsonReader.beginArray();
                TextComponent.TextComponentBuilder textComponentMain = read(jsonReader).toBuilder();

                List<TextComponent> textComponentExtra = new ArrayList<>();
                while (jsonReader.peek() != JsonToken.END_ARRAY) textComponentExtra.add(read(jsonReader));

                textComponentMain.extra(textComponentExtra);
                jsonReader.endArray();
                return textComponentMain.build();
            }
            case BEGIN_OBJECT -> {
                return gson.getDelegateAdapter(new TextComponentAdapterFactory(), new TypeToken<TextComponent>() {}).read(jsonReader).toBuilder().build();
            }
            case STRING -> {
                return TextComponent.fromString(jsonReader.nextString());
            }
            default ->
                    throw new JsonParseException("Expected BEGIN_ARRAY, BEGIN_OBJECT, or STRING, but got " + jsonReader.peek());
        }
    }

    public static class TextComponentAdapterFactory implements TypeAdapterFactory {
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            //noinspection unchecked
            return (TypeAdapter<T>) new TextComponentAdapter(gson);
        }
    }
}