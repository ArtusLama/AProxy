package de.artus.proxy.client.ping;

import de.artus.proxy.util.textcomponent.TextComponent;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ServerListResponse {

    @Getter private Version version;
    @Getter private boolean enforcesSecureChat;
    @Getter private boolean previewsChat;
    @Getter private TextComponent description; // create/use TextComponents

    @Getter private Players players;



    @AllArgsConstructor
    public static class Version {
        @Getter
        private String name;
        @Getter
        private int protocol;
    }

    @AllArgsConstructor
    public static class Players {
        @Getter
        private int max;
        @Getter
        private int online;
        @Getter
        private Player[] sample;
    }
    @AllArgsConstructor
    public static class Player {
        @Getter
        private String name;
        @Getter
        private String id;
    }

}
