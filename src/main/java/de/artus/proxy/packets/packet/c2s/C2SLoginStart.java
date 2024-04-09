package de.artus.proxy.packets.packet.c2s;

import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.UUIDField;
import de.artus.proxy.packets.packet.Packet;
import lombok.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class C2SLoginStart extends Packet {

    private StringField name;
    private UUIDField uuid;

    @Override
    public C2SLoginStart read(DataInputStream stream) throws IOException {
        getName().read(stream);
        getUuid().read(stream);

        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        getName().write(stream);
        getUuid().write(stream);
    }
}
