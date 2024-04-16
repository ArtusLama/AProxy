package de.artus.proxy.util.textcomponent;


import com.google.gson.Gson;
import de.artus.proxy.util.textcomponent.events.ClickAction;
import de.artus.proxy.util.textcomponent.events.ClickEvent;
import de.artus.proxy.util.textcomponent.events.HoverAction;
import de.artus.proxy.util.textcomponent.events.HoverEvent;
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
        var expected = TextComponent.builder().text("Hello").extraString(" World!").build();
        assertEquals(expected, GSON.fromJson("""
                ["Hello", " World!"]""", TextComponent.class));
    }

    @Test
    public void nestedArray() {
        var expected = TextComponent.builder().text("Hii!").extraString(" It's meee").extraString(" nested arrays!!").build();

        assertEquals(expected, GSON.fromJson("""
                [["Hii!", {"text": " It's meee"}], " nested arrays!!"]""", TextComponent.class));
    }

    @Test
    public void stringShorthand() {
        var expected = TextComponent.fromString("Hello!");

        assertEquals(expected, GSON.fromJson("\"Hello!\"", TextComponent.class));
    }

    @Test
    public void textModifier() {
        var expected = TextComponent.builder().text("Cool looking text").bold(true).italic(true).obfuscated(true).underlined(true).strikethrough(true).build();

        assertEquals(expected, GSON.fromJson("""
                {"text": "Cool looking text", "bold": true, "italic": true, "obfuscated": true, "underlined": true, "strikethrough": true}""", TextComponent.class));
    }

    @Test
    public void clickEvent() {
        var expected = TextComponent.builder().text("Click me!").clickEvent(new ClickEvent(ClickAction.open_url, "https://google.com")).build();

        assertEquals(expected, GSON.fromJson("""
                {"text": "Click me!", clickEvent: {"action": "open_url", "value": "https://google.com"}}""", TextComponent.class));
    }

    @Test
    public void hoverEvent() {
        var expected = TextComponent.builder().text("Hover over me!").hoverEvent(new HoverEvent(HoverAction.show_text, TextComponent.builder().text("I'm dark purple!").color(Color.dark_purple).build())).build();

        assertEquals(expected, GSON.fromJson("""
                {"text": "Hover over me!", "hoverEvent": {"action": "show_text", "contents": {"text": "I'm dark purple!", "color": "dark_purple"}}}""", TextComponent.class));
    }

    @Test
    public void fonts() {
        var expected = TextComponent.builder().text("Another font").font(TextComponent.FONT_ALT).build();

        assertEquals(expected, GSON.fromJson("""
                {"text": "Another font", "font": "minecraft:alt"}""", TextComponent.class));
    }

    @Test
    public void selectors() {
        var expected = TextComponent.builder().selector("@a").build();

        assertEquals(expected, GSON.fromJson("""
                {"selector": "@a"}""", TextComponent.class));
    }

    @Test
    public void serializeThenDeserialize() {
        var textComponentTest = TextComponent.builder().text("Hiii!").color(Color.blue).extraString(" It's meee!").bold(true).type(ContentType.text).build();

        assertEquals(textComponentTest, GSON.fromJson(GSON.toJson(textComponentTest), TextComponent.class));
    }

    @Test
    public void score() {
        var expected = TextComponent.builder().score(new Score("Herobrine", "deaths")).color(Color.red).bold(true).build();

        assertEquals(expected, GSON.fromJson("""
                {"score":{"name":"Herobrine","objective":"deaths"}, "bold": true, "color": "red"}""", TextComponent.class));
    }
}
