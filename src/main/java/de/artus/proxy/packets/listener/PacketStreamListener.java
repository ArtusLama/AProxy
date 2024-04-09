package de.artus.proxy.packets.listener;

import de.artus.proxy.packets.packet.Packet;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;


@Slf4j
@RequiredArgsConstructor
public abstract class PacketStreamListener {

    @Getter
    private final DataInputStream stream;
    @Getter
    private final PacketManager packetManager;

    @Getter
    @Setter
    private volatile boolean listening;

    @Getter @Setter
    private String threadName = "PacketListener";

    public void listen() {
        log.info("Starting PacketListener '{}'...", getThreadName());
        Thread thread = new Thread(this::listenThread);
        thread.setName(getThreadName());
        thread.start();
    }

    protected abstract void listenThread();

    protected void onPacketReceived(Packet packet) {
        getPacketManager().receivePacket(packet).ifPresent(this::setCurrentPacket);
    }

    @Getter
    @Setter
    private volatile Packet currentPacket;
    public <T extends Packet> T expectPacket(Class<T> packetClass) {
        log.trace("Expecting next Packet to be: {}...", packetClass.getSimpleName());
        setCurrentPacket(null);
        while (getCurrentPacket() == null) {}

        if (packetClass.isInstance(getCurrentPacket())) {
            return packetClass.cast(getCurrentPacket());
        }

        log.error("Expected Packet {}, but got {}", packetClass.getSimpleName(), getCurrentPacket().getClass().getSimpleName());
        return null;
    }
}
