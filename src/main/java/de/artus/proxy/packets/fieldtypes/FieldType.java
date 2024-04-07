package de.artus.proxy.packets.fieldtypes;

import lombok.AllArgsConstructor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public interface FieldType<T> {

    T getValue();
    FieldType<T> read(DataInputStream stream) throws IOException;

    FieldType<T> write(DataOutputStream stream) throws IOException;
}
