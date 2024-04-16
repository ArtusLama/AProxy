package de.artus.proxy.util.textcomponent;

import com.google.gson.annotations.JsonAdapter;
import de.artus.proxy.util.textcomponent.events.ClickEvent;
import de.artus.proxy.util.textcomponent.events.HoverEvent;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNullElse;

@Value
@JsonAdapter(TextComponentAdapter.TextComponentAdapterFactory.class)
@Builder(toBuilder = true)
public class TextComponent {
    public static final String FONT_DEFAULT = "minecraft:default";
    public static final String FONT_UNIFORM = "minecraft:uniform";
    public static final String FONT_ALT = "minecraft:alt";
    public static final String FONT_ILLAGERALT = "minecraft:illageralt";

    public ContentType type;

    @Singular("extra")
    public List<TextComponent> extra;

    public Color color;


    public Boolean bold;
    public Boolean italic;
    public Boolean underlined;
    public Boolean strikethrough;
    public Boolean obfuscated;


    public String font;

    public String insertion;

    public ClickEvent clickEvent;

    public HoverEvent hoverEvent;

    public String text;

    public String translate;
    @Singular("with")
    public List<TextComponent> with;

    public String keybind;

    public Score score;

    // selector content

    public String selector;

    public TextComponent separator;


    // nbt content
    public String nbt;

    public Boolean interpret;

    //public TextComponent separator; // already covered in the selector content section

    // only one of these can be set
    public String block;

    public String entity;

    public String storage;

    public ContentType getContentType() {
        if (this.type != null) return this.type;
        if (this.text != null) return ContentType.text;
        if (this.translate != null) return ContentType.translatable;
        if (this.keybind != null) return ContentType.keybind;
        if (this.score != null) return ContentType.score;
        if (this.selector != null) return ContentType.selector;
        if (this.nbt != null) return ContentType.nbt;
        return null;
    }

    public String getClearText() {
        return getClearTextOf(this);
    }

    public static String getClearTextOf(TextComponent t) {
        if (t.extra == null) return requireNonNullElse(t.text, "");
        return t.text + t.extra.stream().map(TextComponent::getClearText).collect(Collectors.joining());
    }

    public static TextComponent fromString(String s) {
        return builder().text(s).build();
    }

    public static class TextComponentBuilder {
        public TextComponentBuilder extraString(String s) {
            this.extra(TextComponent.fromString(s));
            return this;
        }

        public TextComponentBuilder block(String block) {
            if (entity != null || storage != null)
                throw new IllegalStateException("block, entity, and storage are mutually exclusive");
            this.block = block;
            return this;
        }
        public TextComponentBuilder entity(String entity) {
            if (block != null || storage != null)
                throw new IllegalStateException("block, entity, and storage are mutually exclusive");
            this.entity = entity;
            return this;
        }
        public TextComponentBuilder storage(String storage) {
            if (block != null || entity != null)
                throw new IllegalStateException("block, entity, and storage are mutually exclusive");
            this.storage = storage;
            return this;
        }
    }
}
