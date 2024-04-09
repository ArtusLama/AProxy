package de.artus.proxy.util.textcomponent.events;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ClickEvent {

    public ClickAction action;

    public String value;

}
