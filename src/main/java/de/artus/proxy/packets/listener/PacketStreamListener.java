package de.artus.proxy.packets.listener;

import de.artus.proxy.packets.packet.Packet;
import de.artus.proxy.packets.packet.PacketState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;


@Getter
@Slf4j
@RequiredArgsConstructor
public abstract class PacketStreamListener {

    private final DataInputStream stream;
    private final PacketManager packetManager;
    private final BlockingQueue<Packet> packetQueue = new LinkedBlockingQueue<>();

    @Setter
    private PacketState packetContext;

    @Setter
    private volatile boolean listening;

    @Setter
    private String threadName = "PacketListener";

    public void listen(PacketState packetContext) {
        setPacketContext(packetContext);
        log.info("Starting PacketListener '{}'...", getThreadName());
        Thread thread = new Thread(this::listenThread);
        thread.setName(getThreadName());
        thread.start();
    }

    protected abstract void listenThread();

    protected void onPacketReceived(Packet packet) {
        if (packet instanceof S2CDisconnect dis) {
            setListening(false);
            log.warn("Received Disconnect Packet: {}! Closing packet listener connection!", dis.getReason().getValue().getClearText());
        }
        getPacketManager().receivePacket(packet).ifPresent(packetQueue::add);
    }

    @SneakyThrows
    public <T extends Packet> T expectPacket(Class<T> packetClass) {
        log.trace("Expecting next Packet to be: {}...", packetClass.getSimpleName());
        Packet currentPacket = packetQueue.take();

        if (packetClass.isInstance(currentPacket)) return packetClass.cast(currentPacket);

        log.error("Expected Packet {}, but got {}", packetClass.getSimpleName(), currentPacket.getClass().getSimpleName());
        return null;
    }
}
