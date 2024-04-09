package de.artus.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import de.artus.proxy.client.ping.ServerListPingClient;
import de.artus.proxy.client.ping.ServerPingClient;
import de.artus.proxy.packets.fieldtypes.StringField;
import de.artus.proxy.packets.fieldtypes.UShortField;
import de.artus.proxy.packets.fieldtypes.VarIntField;
import de.artus.proxy.packets.packet.c2s.C2SHandshake;
import de.artus.proxy.util.SRVLookup;
import de.artus.proxy.util.ServerAddress;
import lombok.extern.slf4j.Slf4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

@Slf4j
public class TestMainClass {


    public static void main(String[] args) throws IOException {
        /*ServerListPingClient serverListPingClient = new ServerListPingClient(new ServerAddress("smashmc.eu", 25565));

        serverListPingClient.getPacketManager().registerHandler(new TestPacketListener());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonOutput = gson.toJson(serverListPingClient.ping());
        log.info("Server Ping Result:\n" + jsonOutput);*/

        ServerPingClient p = new ServerPingClient(new ServerAddress("localhost", 25565));
        p.getPacketManager().registerHandler(new TestPacketListener());
        float ping = p.ping();
        log.info("Server Ping Result: " + ping + "ms");

    }

}
