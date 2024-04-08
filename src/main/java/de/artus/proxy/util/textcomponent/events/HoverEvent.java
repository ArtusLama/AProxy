package de.artus.proxy.util.textcomponent.events;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoverEvent {

    public HoverAction action;
    public String contents;


}
