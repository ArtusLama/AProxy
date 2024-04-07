package de.artus.proxy.packets.s2c;

import de.artus.proxy.packets.Packet;
import de.artus.proxy.packets.fieldtypes.StringField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class S2CStatusResponse extends Packet {

    private StringField json_response;

    @Override
    public S2CStatusResponse read(DataInputStream stream) throws IOException {
        json_response = new StringField().read(stream);

        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {

    }
}
