package de.artus.proxy.util.textcomponent;

import de.artus.proxy.util.textcomponent.events.ClickEvent;
import de.artus.proxy.util.textcomponent.events.HoverEvent;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class TextComponent {
    public ContentType type;

    public TextComponent[] extra;

    public Color color;


    public Boolean bold;
    public Boolean italic;
    public Boolean underlined;
    public Boolean strikethrough;
    public Boolean obfuscated;


    public Font font;

    public String insertion;

    public ClickEvent clickEvent;

    public HoverEvent hoverEvent;

    public String text;

    public String translate;
    public TextComponent[] with;

    public String keybind;

    public Score score;

    public SelectorContent selector;

    public NBTContent nbt;


    public TextComponent(String text) {
        this.text = text;
        this.type = ContentType.text;
    }

    public String getClearText() {
        return getClearText(this);
    }
    public String getClearText(TextComponent t) {
        if (t.extra == null) return t.text;
        return t.text + Arrays.stream(t.extra).map(TextComponent::getClearText).collect(Collectors.joining());
    }

}
