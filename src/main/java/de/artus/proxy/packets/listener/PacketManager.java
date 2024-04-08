package de.artus.proxy.packets.listener;

import de.artus.proxy.packets.packet.Packet;
import de.artus.proxy.packets.packet.UnknownPacket;
import de.artus.proxy.packets.packet.c2s.C2SPackets;
import de.artus.proxy.packets.packet.s2c.S2CPackets;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@Slf4j
public class PacketManager {

    @Getter
    private final List<PacketListener> handlers = new ArrayList<>();


    public void registerHandler(PacketListener handler) {
        log.trace("Registering PacketListener {}", handler.getClass().getName());
        getHandlers().add(handler);
    }

    private boolean handlePacket(Packet packet) {
        if (packet instanceof UnknownPacket) return true;

        Set<Class<? extends Packet>> allPackets = new HashSet<>();
        allPackets.addAll(C2SPackets.getAllClasses());
        allPackets.addAll(S2CPackets.getAllClasses());


        for (Class<? extends Packet> packetClass: allPackets) {
            if (!packetClass.isInstance(packet)) continue;
            for (PacketListener handler : getHandlers()) {
                log.trace("Calling packet handler {}", handler.getClass().getName());
                for (Method method : handler.getClass().getMethods()) {
                    if (!method.isAnnotationPresent(OnPacket.class)) continue;
                    log.trace("Found PacketListener {} in class {}", method.getName(), handler.getClass().getName());
                    if (method.getParameterCount() != 1) {
                        log.warn("PacketListener {} in class {} has wrong parameter count! Skipping PacketListener!", method.getName(), handler.getClass().getName());
                        return true;
                    }
                    if (method.getReturnType().isAssignableFrom(PacketEventResult.class) || method.getReturnType().isAssignableFrom(Void.TYPE)) {
                        log.trace("PacketListener {} has the correct signature", handler.getClass().getName());
                        log.trace("PacketListener {}: checking for {}", handler.getClass().getName(), packetClass.getSimpleName());
                        if (method.getParameterTypes()[0] == packetClass) {
                            try {
                                log.trace("Invoking listener with method type {}", packetClass.getSimpleName());
                                if (method.getReturnType().isAssignableFrom(Void.TYPE)) method.invoke(handler.getClass().getConstructor().newInstance(),packetClass.cast(packet));
                                else {
                                    PacketEventResult result = (PacketEventResult) method.invoke(handler.getClass().getConstructor().newInstance(),packetClass.cast(packet));
                                    if (result == PacketEventResult.CANCELLED) return false;
                                }
                            } catch (IllegalAccessException e) {
                                log.error("No permissions to call Listener??? Exiting.....", e);
                                System.exit(-1);
                            } catch (InvocationTargetException e) {
                                log.error("Listener threw an Exception!", e);
                            } catch (InstantiationException e) {
                                log.error("Could not instantiate Handler {}", handler.getClass().getName(), e);
                            } catch (NoSuchMethodException e) {
                                log.error("Handler {} does not have a no args constructor defined!", handler.getClass().getName(), e);
                            }
                        }
                    } else {
                        log.warn("PacketListener {} in class {} has wrong return type! Skipping PacketListener!", method.getName(), handler.getClass().getName());
                        return true;
                    }
                }
            }
        }



        return true;
    }

    public void sendPacket(Packet packet, DataOutputStream out) throws IOException {
        log.trace("Handling packet {}...", packet.getClass().getSimpleName());
        if (handlePacket(packet)) {
            log.trace("Packet {} is going to be sent!", packet.getClass().getSimpleName());
            packet.send(out);
        } else log.trace("Packet {} was cancelled!", packet.getClass().getSimpleName());
    }

    public Optional<Packet> receivePacket(Packet packet) {
        return Optional.of(packet).filter(this::handlePacket);
    }
}
