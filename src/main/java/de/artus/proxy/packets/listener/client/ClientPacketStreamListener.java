package de.artus.proxy.packets.listener.client;

import de.artus.proxy.packets.packet.UnknownPacket;
import de.artus.proxy.packets.packet.Packet;
import de.artus.proxy.packets.fieldtypes.VarIntField;
import de.artus.proxy.packets.listener.PacketManager;
import de.artus.proxy.packets.listener.PacketStreamListener;
import de.artus.proxy.packets.packet.s2c.S2CPackets;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;

@Slf4j
public class ClientPacketStreamListener extends PacketStreamListener {


    public ClientPacketStreamListener(DataInputStream stream, PacketManager packetManager) {
        super(stream, packetManager);
        setThreadName("S -> C");
    }

    protected void listenThread() {
        try {
            setListening(true);
            while (isListening()) {

                while (getStream().available() == 0) { // not 'the yellow from the egg' ;D
                    if (!isListening()) return;
                }

                int packetLength = new VarIntField().read(getStream()).getValue();
                int packetId = new VarIntField().read(getStream()).getValue();
                log.trace("Incoming Packet: Size={} Id={}", packetLength, "0x" + Integer.toHexString(packetId));


                Class<? extends Packet> packetClass = S2CPackets.getPacketById(packetId, getPacketContext());
                if (packetClass == UnknownPacket.class) {
                    log.warn("Unknown Packet ID: {}! Using UnknownPacket", "0x" + Integer.toHexString(packetId));
                    onPacketReceived(new UnknownPacket().setLength(packetLength).setId(packetId).read(getStream()));
                    continue;
                }
                try {
                    Packet packet = packetClass.getDeclaredConstructor().newInstance().read(getStream());
                    log.trace("Received Packet: {}", packet.getClass().getSimpleName());
                    onPacketReceived(packet);
                } catch (NoSuchMethodException e) {
                    log.error("Packet {} wasn't declared correctly! (Use @NoArgsConstructor)", packetClass.getSimpleName());
                }
            }
        } catch (Exception e) {
            log.error("Error while listening for packets", e);
        }
    }
}
