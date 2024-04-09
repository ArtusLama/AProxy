package de.artus.example;

import de.artus.proxy.packets.listener.OnPacket;
import de.artus.proxy.packets.listener.PacketEventResult;
import de.artus.proxy.packets.listener.PacketListener;

import de.artus.proxy.packets.packet.s2c.S2CPingResponse;
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
    public void onPacket2(S2CPingResponse packet) {
        // VOID => Never cancels!
        log.info(packet.getPingStartTime().toString(), packet);
    }
}
