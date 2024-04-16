package de.artus.proxy.util.textcomponent.events;

import lombok.*;

@Value
public class ClickEvent {
    public ClickAction action;

    public String value;
}
