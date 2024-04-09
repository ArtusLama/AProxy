package de.artus.proxy.client.play;

import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.UShortField;
import de.artus.proxy.packets.fieldtypes.VarIntField;
import de.artus.proxy.packets.listener.PacketManager;
import de.artus.proxy.packets.packet.c2s.C2SHandshake;
import de.artus.proxy.util.ServerAddress;
import lombok.Getter;
import lombok.Setter;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MinecraftClient {

    @Getter @Setter
    private boolean connected;
    @Getter @Setter
    private Socket connection;


    @Getter
    private PacketManager packetManager = new PacketManager();


    public void connectTo(ServerAddress server) throws IOException {
        if (isConnected()) disconnect();

        setConnection(new Socket());
        DataOutputStream outputStream = new DataOutputStream(getConnection().getOutputStream());


        // Step 1: Send handshake packet
        getPacketManager().sendPacket(new C2SHandshake(
                new VarIntField(-1), // not planning to log in, so the version doesn't matter
                new StringField(server.getHost()),
                new UShortField(server.getPort()),
                new VarIntField(2) // status = 2 to indicate that we want to log in
        ), outputStream);

        // Step 2: Send login start packet
        // ...

    }

    public void disconnect() {
        // implement logic to disconnect from the server
    }

}
