package de.artus.proxy.util.textcomponent.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClickEvent {

    public ClickAction action;

    public String value;

}
