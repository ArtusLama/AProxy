package de.artus.proxy.util.textcomponent;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Font {
    DEFAULT("minecraft:default"), UNIFORM("minecraft:uniform"), ALT("minecraft:alt"), ILLAGERALT("minecraft:illageralt");

    @Getter
    private final String fontIdentifier;
}
