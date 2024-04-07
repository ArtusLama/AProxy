package de.artus.proxy;


import de.artus.proxy.packets.c2s.C2SHandshake;
import de.artus.proxy.packets.c2s.C2SStatusRequest;
import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.UShortField;
import de.artus.proxy.packets.fieldtypes.VarIntField;
import de.artus.proxy.packets.s2c.S2CStatusResponse;
import de.artus.proxy.util.SRVLookup;
import lombok.extern.slf4j.Slf4j;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HexFormat;

@Slf4j
public class AProxy {
    public static void main(String[] args) throws IOException {

        //new ServerListPing(SRVLookup.lookup("smashmc.eu", 25565)).fetchData();
        Socket socket = new Socket();
        socket.connect(SRVLookup.lookup("localhost", 25565), 7000);


        // Get Input and Output Stream
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        DataInputStream in = new DataInputStream(socket.getInputStream());

        new C2SHandshake(
                new VarIntField(4),
                new StringField("localhost"),
                new UShortField(25565),
                new VarIntField(1)
        ).send(out);

        new C2SStatusRequest().send(out);

        int packetSize = new VarIntField().read(in).getValue();
        int packetId = new VarIntField().read(in).getValue();
        log.trace("Incoming Packet: Size={} Id={}", packetSize, packetId);
        log.info("Status Response JSON: " + new S2CStatusResponse().read(in).getJson_response().getValue());
    }
}
