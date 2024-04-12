package de.artus.proxy.util.textcomponent;

import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TextComponentSerializationTest {
    final Gson GSON = new Gson();

    @Test
    public void jsonObject() {
        assertEquals("""
                {"text":"Hewo"}""", GSON.toJson(TextComponent.fromString("Hewo")));
    }
}
