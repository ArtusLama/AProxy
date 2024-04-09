package de.artus.proxy.packets.packet.c2s;

import de.artus.proxy.packets.fieldtypes.LongField;
import de.artus.proxy.packets.packet.Packet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class C2SPingRequest extends Packet {


    private LongField pingStartTime = new LongField();

    @Override
    public Packet read(DataInputStream stream) throws IOException {
        getPingStartTime().read(stream);
        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        getPingStartTime().write(stream);
    }
}
