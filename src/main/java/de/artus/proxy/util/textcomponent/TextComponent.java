package de.artus.proxy.util.textcomponent;

import com.google.gson.annotations.JsonAdapter;
import de.artus.proxy.util.textcomponent.events.ClickEvent;
import de.artus.proxy.util.textcomponent.events.HoverEvent;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

@Value
@JsonAdapter(TextComponentAdapterFactory.class)
@Builder(toBuilder = true)
public class TextComponent {
    public ContentType type;

    @Singular("extra")
    public List<TextComponent> extra;

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
    @Singular("with")
    public List<TextComponent> with;

    public String keybind;

    public Score score;

    public SelectorContent selector;

    public NBTContent nbt;

    public String getClearText() {
        return getClearTextOf(this);
    }

    public static String getClearTextOf(TextComponent t) {
        if (t.extra == null) return t.text;
        return t.text + t.extra.stream().map(TextComponent::getClearText).collect(Collectors.joining());
    }

    public static TextComponent fromString(String s) {
        return builder().text(s).build();
    }
}
