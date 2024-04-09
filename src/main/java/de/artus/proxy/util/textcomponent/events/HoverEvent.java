package de.artus.proxy.util.textcomponent.events;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class HoverEvent {

    public HoverAction action;
    public String contents;


}
