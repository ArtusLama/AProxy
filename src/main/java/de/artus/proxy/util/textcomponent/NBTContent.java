package de.artus.proxy.util.textcomponent;

import lombok.Data;

@Data
public class NBTContent {

    public String nbt;

    public boolean interpret;

    public TextComponent separator;

    // only one of these can be set
    public String block;

    public String entity;

    public String storage;

}
