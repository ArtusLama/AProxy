package de.artus.proxy.packets.c2s;

import de.artus.proxy.packets.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class C2SStatusRequest extends Packet {


    @Override
    public C2SStatusRequest read(DataInputStream stream) throws IOException {
        return this;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
    }
}
