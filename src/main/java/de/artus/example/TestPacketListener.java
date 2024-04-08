package de.artus.example;

import de.artus.proxy.packets.listener.OnPacket;
import de.artus.proxy.packets.listener.PacketEventResult;
import de.artus.proxy.packets.listener.PacketListener;

import de.artus.proxy.packets.packet.s2c.S2CStatusResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestPacketListener implements PacketListener {

    public void thisIsNotListening() {
        System.out.println("lol xD");
    }

    @OnPacket
    public PacketEventResult onPacket(S2CStatusResponse packet) {
        log.info("Received StatusResponse as packet listener: {}", packet);
        return PacketEventResult.PASS;
    }
    @OnPacket
    public PacketEventResult onPacket2(S2CStatusResponse packet) {
        log.info("I am going to CANCEL the packet: {}", packet);
        return PacketEventResult.CANCELLED;
    }
}
