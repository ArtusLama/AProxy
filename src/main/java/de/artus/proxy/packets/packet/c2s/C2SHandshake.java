package de.artus.proxy.packets.packet.c2s;

import de.artus.proxy.packets.packet.Packet;
import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.UShortField;
import de.artus.proxy.packets.fieldtypes.VarIntField;
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
public class C2SHandshake extends Packet {

    private VarIntField protocolVersion = new VarIntField();
    private StringField serverAddress = new StringField();
    private UShortField port = new UShortField();
    private VarIntField nextState = new VarIntField();

    @Override
    public C2SHandshake read(DataInputStream stream) throws IOException {
        getProtocolVersion().read(stream);
        getServerAddress().read(stream);
        getPort().read(stream);
        getNextState().read(stream);

        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException{
        getProtocolVersion().write(stream);
        getServerAddress().write(stream);
        getPort().write(stream);
        getNextState().write(stream);
    }
}
