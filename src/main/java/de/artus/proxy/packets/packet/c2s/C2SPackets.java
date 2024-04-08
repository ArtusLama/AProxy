package de.artus.proxy.packets.packet.c2s;

import de.artus.proxy.packets.packet.Packet;
import de.artus.proxy.packets.packet.UnknownPacket;
import de.artus.proxy.packets.packet.s2c.S2CPackets;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

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
        for (S2CPackets s2CPackets : S2CPackets.values()) {
            if (s2CPackets.getId() == id)
                return s2CPackets.getPacketClass();
        }
        return UnknownPacket.class;
    }

    public static Set<Class<? extends Packet>> getAllClasses() {
        return Arrays.stream(values()).map(C2SPackets::getPacketClass).collect(Collectors.toSet());
    }


}
