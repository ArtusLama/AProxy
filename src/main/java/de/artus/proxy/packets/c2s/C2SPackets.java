package de.artus.proxy.packets.c2s;

import de.artus.proxy.packets.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum C2SPackets {


    HANDSHAKE(0x00, C2SHandshake.class),
    STATUS_REQUEST(0x00, C2SStatusRequest.class)

    ;

    @Getter
    private final int id;

    @Getter
    private final Class<? extends Packet> packetClass;

    public static int getIdByPacket(Class<? extends Packet> packet) {
        return Arrays.stream(C2SPackets.values()).filter(c2sPacket -> c2sPacket.getPacketClass().equals(packet)).findFirst().orElseThrow(() -> new IllegalArgumentException("Packet not found")).getId();
    }
    public static Class<? extends Packet> getPacketById(int id) {
        return Arrays.stream(C2SPackets.values()).filter(c2sPacket -> c2sPacket.getId() == id).findFirst().orElseThrow(() -> new IllegalArgumentException("Packet not found")).getPacketClass();
    }


}
