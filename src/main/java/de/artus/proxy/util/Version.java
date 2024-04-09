package de.artus.proxy.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Version {

    v_1_8("1.8", 47),
    v_1_20_1("1.20.1", 763),
    v_1_20_4("1.20.4", 765)

    ;

    @Getter
    private final String name;

    @Getter
    private final int protocol;

}
