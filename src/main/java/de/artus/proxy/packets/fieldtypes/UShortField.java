package de.artus.proxy.packets.fieldtypes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@NoArgsConstructor
@AllArgsConstructor
public class UShortField implements FieldType<Integer> {

    @Accessors(chain = true)
    @Getter @Setter
    private Integer value;


    @Override
    public UShortField read(DataInputStream stream) throws IOException {
        setValue(stream.readUnsignedShort());

        return this;
    }

    @Override
    public UShortField write(DataOutputStream stream) throws IOException {
        stream.writeShort(getValue());

        return this;
    }
}
