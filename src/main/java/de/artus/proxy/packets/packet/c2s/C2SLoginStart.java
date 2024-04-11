package de.artus.proxy.packets.packet.c2s;

import de.artus.proxy.packets.fieldtypes.BooleanField;
import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.UUIDField;
import de.artus.proxy.packets.packet.Packet;
import lombok.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class C2SLoginStart extends Packet {

    private StringField name;
    private BooleanField hasUUID;
    private UUIDField uuid;

    @Override
    public C2SLoginStart read(DataInputStream stream) throws IOException {
        getName().read(stream);
        getHasUUID().read(stream);
        if (getHasUUID().getValue()) {
            getUuid().read(stream);
        } else setUuid(new UUIDField(UUID.randomUUID()));

        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        getName().write(stream);
        getHasUUID().write(stream);
        if (getHasUUID().getValue()) {
            getUuid().write(stream);
        } else {
            new UUIDField(UUID.randomUUID()).write(stream);
        }
    }
}
