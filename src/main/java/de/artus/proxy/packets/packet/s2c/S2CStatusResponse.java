package de.artus.proxy.packets.packet.s2c;

import com.google.gson.Gson;
import de.artus.proxy.client.ping.ServerListResponse;
import de.artus.proxy.packets.packet.Packet;
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

    private ServerListResponse response;
    private static final Gson gson = new Gson();

    @Override
    public S2CStatusResponse read(DataInputStream stream) throws IOException {
        response = gson.fromJson(new StringField().read(stream).getValue(), ServerListResponse.class);

        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        new StringField(gson.toJson(response)).write(stream);
    }
}
