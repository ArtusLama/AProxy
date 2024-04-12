package de.artus.proxy.util.textcomponent;


import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TextComponentDeserializationTest {
    final Gson GSON = new Gson();

    @Test
    public void simpleJsonObject() {
        assertEquals(new TextComponent("Hello!"), GSON.fromJson("""
                {"text":"Hello!"}""", TextComponent.class));
    }

    @Test
    public void JsonObjectTextWithColor() {
        TextComponent expected = new TextComponent("Hello!");
        expected.color = Color.blue;
        assertEquals(expected, GSON.fromJson("""
                {"text":"Hello!", "color": "blue"}""", TextComponent.class));
    }

    @Test
    public void arrayTextComponent() {
        TextComponent expected = new TextComponent("Hello");
        expected.extra = new TextComponent[]{new TextComponent(" World!")};
        assertEquals(expected, GSON.fromJson("""
                ["Hello", " World!"]""", TextComponent.class));
    }

    @Test
    public void stringShorthand() {
        TextComponent expected = new TextComponent("Hello!");

        assertEquals(expected, GSON.fromJson("\"Hello!\"", TextComponent.class));
    }
}
