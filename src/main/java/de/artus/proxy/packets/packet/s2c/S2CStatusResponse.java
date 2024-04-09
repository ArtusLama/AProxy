package de.artus.proxy.packets.packet.s2c;

import com.google.gson.Gson;
import de.artus.proxy.client.ping.ServerListResponse;
import de.artus.proxy.packets.packet.Packet;
import de.artus.proxy.packets.fieldtypes.StringField;
import lombok.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class S2CStatusResponse extends Packet {

    @Getter @Setter
    private StringField stringResponse = new StringField();

    @Getter @Setter(AccessLevel.PRIVATE)
    private ServerListResponse response;

    @Override
    public S2CStatusResponse read(DataInputStream stream) throws IOException {
        getStringResponse().read(stream);
        setResponse(new Gson().fromJson(getStringResponse().getValue(), ServerListResponse.class));

        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        getStringResponse().write(stream);
    }
}
