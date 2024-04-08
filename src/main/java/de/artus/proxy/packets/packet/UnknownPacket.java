package de.artus.proxy.packets.packet;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class UnknownPacket extends Packet {

    // This is a packet that is used to read unknown packets
    @Accessors(chain = true)
    @Getter @Setter
    private int id;

    @Accessors(chain = true)
    @Getter @Setter
    private int length;


    @Getter
    private byte[] data;

    @Override
    public Packet read(DataInputStream stream) throws IOException {
        data = stream.readAllBytes();
        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        stream.write(getData());
    }
}
