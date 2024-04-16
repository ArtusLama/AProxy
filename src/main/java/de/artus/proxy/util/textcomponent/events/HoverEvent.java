package de.artus.proxy.util.textcomponent.events;

import de.artus.proxy.util.textcomponent.TextComponent;
import lombok.Value;

@Value
public class HoverEvent {
    public HoverAction action;
    public TextComponent contents;
}
