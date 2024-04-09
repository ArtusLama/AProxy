package de.artus.proxy.util.textcomponent;


import com.google.gson.Gson;
import de.artus.proxy.packets.fieldtypes.JsonTextComponentField;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTextComponentDeserializer {

    final Gson GSON = JsonTextComponentField.GSON;

    @Test
    public void normalJsonObject() {
        assertEquals(new TextComponent("Hello!"), GSON.fromJson("""
                {"text":"Hello!"}""", TextComponent.class));
    }

    @Test
    public void normalJsonObjectWithColor() {
        TextComponent expected = new TextComponent("Hello!");
        expected.color = Color.blue;
        assertEquals(expected, GSON.fromJson("""
                {"text":"Hello!", "color": "blue"}""", TextComponent.class));
    }
}
