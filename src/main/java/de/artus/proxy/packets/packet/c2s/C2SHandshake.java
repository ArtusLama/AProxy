package de.artus.proxy.packets.packet.c2s;

import de.artus.proxy.packets.packet.Packet;
import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.UShortField;
import de.artus.proxy.packets.fieldtypes.VarIntField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class C2SHandshake extends Packet {

    private VarIntField protocol_version;   // VarInt
    private StringField server_address;     // String
    private UShortField port;               // unsigned short
    private VarIntField next_state;         // VarInt

    @Override
    public C2SHandshake read(DataInputStream stream) throws IOException {
        protocol_version = new VarIntField().read(stream);
        server_address = new StringField().read(stream);
        port = new UShortField().read(stream);
        next_state = new VarIntField().read(stream);

        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException{
        protocol_version.write(stream);
        server_address.write(stream);
        port.write(stream);
        next_state.write(stream);
    }
}
