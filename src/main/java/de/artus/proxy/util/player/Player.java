package de.artus.proxy.util.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    private String name;
    private UUID uuid;

}
