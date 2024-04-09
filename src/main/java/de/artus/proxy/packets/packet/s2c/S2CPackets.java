package de.artus.proxy.packets.packet.s2c;

import de.artus.proxy.packets.packet.Packet;
import de.artus.proxy.packets.packet.UnknownPacket;

import de.artus.proxy.packets.packet.c2s.C2SPingRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public enum S2CPackets {


    STATUS_RESPONSE(0x00, S2CStatusResponse.class),
    PING_RESPONSE(0x01, S2CPingResponse .class)

    ;

    @Getter
    private final int id;

    @Getter
    private final Class<? extends Packet> packetClass;

    public static int getIdByPacket(Class<? extends Packet> packet) {
        return Arrays.stream(S2CPackets.values()).filter(c2sPacket -> c2sPacket.getPacketClass().equals(packet)).findFirst().orElseThrow(() -> new IllegalArgumentException("Packet not found")).getId();
    }
    public static Class<? extends Packet> getPacketById(int id) {
        for (S2CPackets s2CPackets : S2CPackets.values()) {
            if (s2CPackets.getId() == id)
                return s2CPackets.getPacketClass();
        }
        return UnknownPacket.class;
    }


    public static Set<Class<? extends Packet>> getAllClasses() {
        return Arrays.stream(values()).map(S2CPackets::getPacketClass).collect(Collectors.toSet());
    }


}
