package de.artus.proxy.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ServerAddress {

    @Getter
    private String host;

    @Getter
    private int port;

}
