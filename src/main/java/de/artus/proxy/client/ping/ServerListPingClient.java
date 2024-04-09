package de.artus.proxy.client.ping;

import com.google.gson.Gson;
import de.artus.proxy.packets.packet.PacketState;
import de.artus.proxy.packets.packet.c2s.C2SHandshake;
import de.artus.proxy.packets.packet.c2s.C2SStatusRequest;
import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.UShortField;
import de.artus.proxy.packets.fieldtypes.VarIntField;
import de.artus.proxy.packets.listener.PacketManager;
import de.artus.proxy.packets.listener.client.ClientPacketStreamListener;
import de.artus.proxy.packets.packet.s2c.S2CStatusResponse;
import de.artus.proxy.util.SRVLookup;
import de.artus.proxy.util.ServerAddress;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Getter
@Slf4j
@RequiredArgsConstructor
public class ServerListPingClient {

    private final ServerAddress server;

    @Setter
    private Socket socket;

    @Setter
    private int timeout = 7000;

    private final PacketManager packetManager = new PacketManager();

    public ServerListResponse ping() throws IOException {
        setSocket(new Socket());
        getSocket().connect(SRVLookup.lookup(getServer()), getTimeout());


        DataOutputStream out = new DataOutputStream(getSocket().getOutputStream());
        DataInputStream in = new DataInputStream(getSocket().getInputStream());

        ClientPacketStreamListener incomingPackets = new ClientPacketStreamListener(in, getPacketManager());
        incomingPackets.listen(PacketState.STATUS);



        getPacketManager().sendPacket(new C2SHandshake(
                new VarIntField(-1), // not planning to log in, so the version doesn't matter
                new StringField(getServer().getHost()),
                new UShortField(getServer().getPort()),
                new VarIntField(1) // 1 for getting status in the next step
        ), out);

        getPacketManager().sendPacket(new C2SStatusRequest(), out);

        // Wait for the response
        S2CStatusResponse response = incomingPackets.expectPacket(S2CStatusResponse.class);

        incomingPackets.setListening(false);
        socket.close();
        return response.getResponse();
    }
}
