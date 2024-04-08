package de.artus.proxy.packets.packet;

import de.artus.proxy.packets.packet.c2s.C2SPackets;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Slf4j
public abstract class Packet {

    public abstract Packet read(DataInputStream stream) throws IOException;
    public abstract void write(DataOutputStream stream) throws IOException;

    public void send(DataOutputStream outputStream) throws IOException {
        ByteArrayOutputStream x = new ByteArrayOutputStream();
        DataOutputStream dataStream = new DataOutputStream(x);

        // Indicate Packet ID
        if (this instanceof UnknownPacket unknownPacket) dataStream.write(unknownPacket.getId());
        else dataStream.write(C2SPackets.getIdByPacket(this.getClass()));

        write(dataStream);

        // Send Packet size
        outputStream.write(x.size());
        // Send Packet
        outputStream.write(x.toByteArray());

    }

    /*
    String hex(byte[] bytes) {
        StringBuilder hexdump = new StringBuilder();
        for (byte b : bytes) {
            hexdump.append(String.format("%-2s", Integer.toHexString(b & 0xff)).replace(' ', '0'));
            hexdump.append(" ");
        }
        return hexdump.toString();
    }*/
}
