package de.artus.proxy.client.play;

import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.UShortField;
import de.artus.proxy.packets.fieldtypes.UUIDField;
import de.artus.proxy.packets.fieldtypes.VarIntField;
import de.artus.proxy.packets.listener.PacketManager;
import de.artus.proxy.packets.listener.client.ClientPacketStreamListener;
import de.artus.proxy.packets.packet.PacketState;
import de.artus.proxy.packets.packet.c2s.C2SHandshake;
import de.artus.proxy.packets.packet.c2s.C2SLoginStart;
import de.artus.proxy.packets.packet.s2c.S2CDisconnect;
import de.artus.proxy.packets.packet.s2c.S2CEncryptionRequest;
import de.artus.proxy.util.SRVLookup;
import de.artus.proxy.util.ServerAddress;
import de.artus.proxy.util.Version;
import de.artus.proxy.util.player.Player;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Getter
@RequiredArgsConstructor
@Slf4j
public class MinecraftClient {

    @Setter
    private boolean connected = false;
    @Setter
    private Socket connection;

    @Getter @Setter @NonNull
    private Player player;

    @Getter @Setter @NonNull
    private Version version;


    private PacketManager packetManager = new PacketManager();


    public void connectTo(ServerAddress server) throws IOException {
        if (isConnected()) disconnect();

        setConnection(new Socket());
        DataOutputStream outputStream = new DataOutputStream(getConnection().getOutputStream());
        ClientPacketStreamListener packetListener = new ClientPacketStreamListener(
                new DataInputStream(getConnection().getInputStream()),
                getPacketManager()
        );

        // Step 1: Send handshake packet
        packetListener.listen(PacketState.LOGIN); // because HANDSHAKE packet can only be received as SERVER so using LOGIN state
        getPacketManager().sendPacket(new C2SHandshake(
                new VarIntField(getVersion().getProtocol()), // not planning to log in, so the version doesn't matter
                new StringField(server.getHost()),
                new UShortField(server.getPort()),
                new VarIntField(2) // status = 2 to indicate that we want to log in
        ), outputStream);

        // Step 2: Send login start packet
        getPacketManager().sendPacket(new C2SLoginStart(
                new StringField(getPlayer().getName()),
                new UUIDField(getPlayer().getUuid())
        ), outputStream);

        // Step 3: Listen for encryption request
        S2CEncryptionRequest encryptionRequest = packetListener.expectPacket(S2CEncryptionRequest.class);
        log.info(new GsonBuilder().setPrettyPrinting().create().toJson(encryptionRequest));


    }

    public void disconnect() {
        // implement logic to disconnect from the server
    }

}
