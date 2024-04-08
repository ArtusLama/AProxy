package de.artus.example;

import de.artus.proxy.client.ping.ServerListPingClient;
import de.artus.proxy.util.ServerAddress;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class TestMainClass {


    public static void main(String[] args) throws IOException {
        ServerListPingClient serverListPingClient = new ServerListPingClient(new ServerAddress("localhost", 25565));

        serverListPingClient.getPacketManager().registerHandler(new TestPacketListener());

        log.info(serverListPingClient.ping().getPlayers().getMax() + "");


        /*ServerListResponse res = new ServerListResponse(new ServerListResponse.Version("lol", 4),
                true, true, new TextComponent("lol"),
                new ServerListResponse.Players(1, 2, new ServerListResponse.Player[0]));

        log.info(new Gson().toJson(res));*/
    }

}
