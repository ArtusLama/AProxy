package de.artus.proxy.client.ping;

import de.artus.proxy.packets.fieldtypes.LongField;
import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.UShortField;
import de.artus.proxy.packets.fieldtypes.VarIntField;
import de.artus.proxy.packets.fieldtypes.custom.CurrentTimeMillisLongField;
import de.artus.proxy.packets.listener.PacketManager;
import de.artus.proxy.packets.listener.client.ClientPacketStreamListener;
import de.artus.proxy.packets.packet.PacketState;
import de.artus.proxy.packets.packet.c2s.C2SHandshake;
import de.artus.proxy.packets.packet.c2s.C2SPingRequest;
import de.artus.proxy.packets.packet.c2s.C2SStatusRequest;
import de.artus.proxy.packets.packet.s2c.S2CPingResponse;
import de.artus.proxy.packets.packet.s2c.S2CStatusResponse;
import de.artus.proxy.util.SRVLookup;
import de.artus.proxy.util.ServerAddress;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
@RequiredArgsConstructor
public class ServerPingClient {

    @Getter
    private final ServerAddress server;

    @Getter @Setter
    private Socket socket;

    @Getter @Setter
    private int timeout = 7000;

    @Getter
    private final PacketManager packetManager = new PacketManager();

    public float ping() throws IOException {
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


        // We need this stupid packets, because some servers won't respond to the ping request without a status request
        getPacketManager().sendPacket(new C2SStatusRequest(), out);
        incomingPackets.expectPacket(S2CStatusResponse.class);




        getPacketManager().sendPacket(new C2SPingRequest(
                new CurrentTimeMillisLongField() // the time we sent the ping request (custom filed because we need the time in milliseconds when actually sending the packet)
        ), out);

        // Wait for the response
        S2CPingResponse response = incomingPackets.expectPacket(S2CPingResponse.class);
        float ping = (System.currentTimeMillis() - response.getPingStartTime().getValue());

        incomingPackets.setListening(false);
        socket.close();
        return ping;
    }

}
