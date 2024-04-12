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
        assertEquals(TextComponent.fromString("Hello!"), GSON.fromJson("""
                {"text":"Hello!"}""", TextComponent.class));
    }

    @Test
    public void JsonObjectTextWithColor() {
        TextComponent expected = TextComponent.builder().text("Hello!").color(Color.blue).build();
        assertEquals(expected, GSON.fromJson("""
                {"text":"Hello!", "color": "blue"}""", TextComponent.class));
    }

    @Test
    public void arrayTextComponent() {
        var expected = TextComponent.builder().text("Hello").extra(TextComponent.fromString(" World!")).build();
        assertEquals(expected, GSON.fromJson("""
                ["Hello", " World!"]""", TextComponent.class));
    }

    @Test
    public void nestedArray() {
        //TextComponent expected = TextComponent.builder().text("Hiii!").extra(new TextComponent[]{new TextComponent(" It's meee"), new TextComponent(" nested arrays!!")}).build();
        var expected = TextComponent.builder().text("Hii!").extra(TextComponent.fromString(" It's meee")).extra(TextComponent.fromString(" nested arrays!!")).build();

        assertEquals(expected, GSON.fromJson("""
                [["Hii!", " It's meee"], " nested arrays!!"]""", TextComponent.class));
    }

    @Test
    public void stringShorthand() {
        var expected = TextComponent.fromString("Hello!");

        assertEquals(expected, GSON.fromJson("\"Hello!\"", TextComponent.class));
    }
}
