package de.artus.proxy.packets.listener;


import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class PacketEventResult {

    public static final PacketEventResult PASS = new PacketEventResult();
    public static final PacketEventResult CANCELLED = new PacketEventResult().setCancelled(true);

    @Accessors(chain = true)
    @Getter @Setter
    private boolean cancelled = false;


}
